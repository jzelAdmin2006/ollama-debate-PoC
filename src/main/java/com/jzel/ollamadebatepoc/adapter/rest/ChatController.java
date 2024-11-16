package com.jzel.ollamadebatepoc.adapter.rest;

import com.jzel.ollamadebatepoc.service.ChatService;
import com.jzel.ollamadebatepoc.service.PartyPlatformRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnExpression("${ollama.debate.poc.directchat.enabled}")
class ChatController {

  private final ChatService chatService;
  private final ChatClient conservativeChatClient;
  private final ChatClient liberalChatClient;
  private final PartyPlatformRagService ragService;

  @PostMapping("/chat/conservative")
  String chatWithConservative(@RequestBody final String input) {
    return chatService.chat(conservativeChatClient, input, ragService.rag(conservativeChatClient, input));
  }

  @PostMapping("/chat/liberal")
  String chatWithLiberal(@RequestBody final String input) {
    return chatService.chat(liberalChatClient, input, ragService.rag(liberalChatClient, input));
  }
}
