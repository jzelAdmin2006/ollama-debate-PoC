package com.jzel.ollamadebatepoc;

import lombok.RequiredArgsConstructor;
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
}
