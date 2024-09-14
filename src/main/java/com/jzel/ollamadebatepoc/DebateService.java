package com.jzel.ollamadebatepoc;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebateService {

  private final ChatService chatService;
  private final ChatClient conservativeChatClient;
  private final ChatClient liberalChatClient;

  String conductDebate(String topic, int exchanges) {
    StringBuilder debateTranscript = new StringBuilder();
    String currentInput = topic;

    for (int i = 0; i < exchanges; i++) {
      String conservativeResponse = chatService.chat(conservativeChatClient, currentInput);
      debateTranscript.append("Conservative: ").append(conservativeResponse).append("\n");

      currentInput = conservativeResponse;

      String liberalResponse = chatService.chat(liberalChatClient, currentInput);
      debateTranscript.append("Liberal: ").append(liberalResponse).append("\n");

      currentInput = liberalResponse;
    }

    return debateTranscript.toString();
  }
}
