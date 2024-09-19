package com.jzel.ollamadebatepoc.service;

import static com.github.pemistahl.lingua.api.Language.ENGLISH;
import static com.github.pemistahl.lingua.api.Language.GERMAN;
import static com.github.pemistahl.lingua.api.LanguageDetectorBuilder.fromLanguages;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.IntStream.range;

import com.github.pemistahl.lingua.api.LanguageDetector;
import com.jzel.ollamadebatepoc.adapter.model.DebateResponse;
import jakarta.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebateService {

  private static final SecureRandom RANDOM = new SecureRandom();
  private static final LanguageDetector LANG_DETECTOR = fromLanguages(ENGLISH, GERMAN).build();

  private final ChatService chatService;
  private final ChatClient conservativeChatClient;
  private final ChatClient liberalChatClient;
  private final PartyPlatformRagService ragService;

  private Map<ChatClient, String> debaterNames;

  @PostConstruct
  void init() {
    debaterNames = Map.of(
        conservativeChatClient, "conservative",
        liberalChatClient, "liberal"
    );
  }

  public List<DebateResponse> conductDebate(final String input, final int exchanges) {
    final String urlEncodedInput = encode(input, UTF_8);
    final List<DebateResponse> debateTranscript = new ArrayList<>();
    final boolean conservativeStarts = RANDOM.nextBoolean();
    final ChatClient firstClient = conservativeStarts ? conservativeChatClient : liberalChatClient;
    final ChatClient secondClient = conservativeStarts ? liberalChatClient : conservativeChatClient;
    final String firstClientRagArguments = ragService.rag(firstClient, input);
    final AtomicReference<String> firstResponse = new AtomicReference<>(answer(firstClient, debateTranscript::add,
        STR."You have been selected to start debating regarding the URL-encoded prompt \"\{urlEncodedInput}\". \{desiredResponseLanguage(
            input)}", firstClientRagArguments
    ));
    final String secondClientRagArguments = ragService.rag(firstClient, input);
    final AtomicReference<String> secondResponse = new AtomicReference<>(answer(secondClient, debateTranscript::add,
        STR."Your opponent was given the URL-encoded prompt \"\{urlEncodedInput}\". Their URL-encoded answer was \"\{
            encode(firstResponse.get(), UTF_8)}\". \{desiredResponseLanguage(input)}", secondClientRagArguments
    ));
    range(1, exchanges).forEach(_ -> {
      firstResponse.set(react(firstClient, secondResponse.get(), debateTranscript::add, firstClientRagArguments));
      secondResponse.set(react(secondClient, firstResponse.get(), debateTranscript::add, secondClientRagArguments));
    });
    return debateTranscript;
  }

  private String react(
      final ChatClient client,
      final String otherResponse,
      final Consumer<DebateResponse> transcriptEntryConsumer,
      String ragArguments) {
    return answer(
        client,
        transcriptEntryConsumer,
        STR."Your opponent has provided the URL-encoded reaction \"\{encode(otherResponse,
            UTF_8)}\" to your arguments. \{desiredResponseLanguage(otherResponse)}",
        ragArguments
    );
  }

  private String answer(
      final ChatClient client,
      final Consumer<DebateResponse> transcriptEntryConsumer,
      final String message,
      final String ragArguments
  ) {
    final String response = chatService.chat(client, message, ragArguments);
    appendResponse(transcriptEntryConsumer, client, response);
    return response;
  }

  private void appendResponse(
      final Consumer<DebateResponse> transcriptEntryConsumer,
      final ChatClient client,
      final String response
  ) {
    transcriptEntryConsumer.accept(new DebateResponse(debaterNames.get(client), response));
  }

  private String desiredResponseLanguage(final String input) {
    return STR."Please respond in \{LANG_DETECTOR.detectLanguageOf(input).toString()}.";
  }
}
