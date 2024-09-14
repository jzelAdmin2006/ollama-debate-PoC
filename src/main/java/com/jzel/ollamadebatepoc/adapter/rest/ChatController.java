package com.jzel.ollamadebatepoc.adapter.rest;

import com.jzel.ollamadebatepoc.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ChatController {

  private final ChatService chatService;
  private final ChatClient conservativeChatClient;
  private final ChatClient liberalChatClient;

  @PostMapping("/chat/conservative")
  String chatWithConservative(@RequestBody final String input) {
    return chatService.chat(conservativeChatClient, input);
  }

  @PostMapping("/chat/liberal")
  String chatWithLiberal(@RequestBody final String input) {
    return chatService.chat(liberalChatClient, input);
  }
}
