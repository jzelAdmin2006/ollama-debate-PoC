package com.jzel.ollamadebatepoc;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DebateService {

  private final ChatService chatService;
  private final ChatClient conservativeChatClient;
  private final ChatClient liberalChatClient;

  List<DebateResponse> conductDebate(String topic, int exchanges) {
    List<DebateResponse> debateTranscript = new ArrayList<>();
    String currentInput = topic;

    for (int i = 0; i < exchanges; i++) {
      String conservativeResponse = chatService.chat(conservativeChatClient, currentInput);
      debateTranscript.add(new DebateResponse("conservative", conservativeResponse));

      currentInput = conservativeResponse;

      String liberalResponse = chatService.chat(liberalChatClient, currentInput);
      debateTranscript.add(new DebateResponse("liberal", liberalResponse));

      currentInput = liberalResponse;
    }

    return debateTranscript;
  }
}
