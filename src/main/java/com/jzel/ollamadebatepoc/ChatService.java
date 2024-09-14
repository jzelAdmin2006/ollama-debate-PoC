package com.jzel.ollamadebatepoc;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChatService {

  private final ChatClient chatClient;

  String chatWithText(final String question) {
    return chatClient.prompt().user(question).call().content();
  }

  ChatResponse chatWithPrompt(final String question) {
    return chatClient.prompt(new Prompt(question)).call().chatResponse();
  }

}
