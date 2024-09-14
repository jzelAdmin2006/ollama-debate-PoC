package com.jzel.ollamadebatepoc;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChatService {

  private final ChatClient chatClient;

  String chatWithText(final String question) {
    return chatClient.prompt().user(question).call().content();
  }
}
