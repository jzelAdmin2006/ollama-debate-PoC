package com.jzel.ollamadebatepoc;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChatService {

  private static final int CHAT_ID = 1;

  String chat(final ChatClient chatClient, final String message) {
    return chatClient
        .prompt()
        .user(message)
        .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, CHAT_ID))
        .call()
        .content();
  }
}
