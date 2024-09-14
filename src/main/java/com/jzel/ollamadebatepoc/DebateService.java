package com.jzel.ollamadebatepoc;

import static java.util.stream.IntStream.range;

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
class DebateService {

  private static final SecureRandom RANDOM = new SecureRandom();

  private final ChatService chatService;
  private final ChatClient conservativeChatClient;
  private final ChatClient liberalChatClient;

  private Map<ChatClient, String> debaterNames;

  @PostConstruct
  void init() {
    debaterNames = Map.of(
        conservativeChatClient, "conservative",
        liberalChatClient, "liberal"
    );
  }

  List<DebateResponse> conductDebate(String input, int exchanges) {
    final List<DebateResponse> debateTranscript = new ArrayList<>();
    final boolean conservativeStarts = RANDOM.nextBoolean();
    final ChatClient firstClient = conservativeStarts ? conservativeChatClient : liberalChatClient;
    final ChatClient secondClient = conservativeStarts ? liberalChatClient : conservativeChatClient;
    final AtomicReference<String> firstResponse = new AtomicReference<>(answer(firstClient, debateTranscript::add,
        STR."You have been selected to start debating regarding the prompt \"\{input}\".."
    ));
    final AtomicReference<String> secondResponse = new AtomicReference<>(answer(secondClient, debateTranscript::add,
        STR."Your opponent was given the prompt \"\{input}\". Their answer was \"\{firstResponse}\"."
    ));
    range(1, exchanges).forEach(_ -> {
      firstResponse.set(react(firstClient, secondResponse.get(), debateTranscript::add));
      secondResponse.set(react(secondClient, firstResponse.get(), debateTranscript::add));
    });
    return debateTranscript;
  }

  private String react(
      final ChatClient client,
      final String otherResponse,
      final Consumer<DebateResponse> transcriptEntryConsumer
  ) {
    return answer(
        client,
        transcriptEntryConsumer,
        STR."Your opponent has provided the reaction \"\{otherResponse}\" to your arguments."
    );
  }

  private String answer(
      final ChatClient client,
      final Consumer<DebateResponse> transcriptEntryConsumer,
      final String message
  ) {
    final String response = chatService.chat(client, message);
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
}
