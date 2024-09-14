package com.jzel.ollamadebatepoc;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ChatController {

  private final ChatService chatService;

  @PostMapping("/chat/simple")
  String chatWithText(@RequestBody final String question) {
    return chatService.chatWithText(question);
  }

  @PostMapping("/chat/prompt")
  String chatWithPrompt(@RequestBody final String question) {
    return chatService.chatWithPrompt(question).getResult().getOutput().getContent();
  }

  @PostMapping("/chat/full")
  ChatResponse chatWithPromptAndFullResponse(@RequestBody final String question) {
    return chatService.chatWithPrompt(question);
  }

}
