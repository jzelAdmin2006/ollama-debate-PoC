package com.jzel.ollamadebatepoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PartyPlatformRagService {

  private final ChatClient liberalChatClient;
  private final WebClient liberalRagClient;
  private final WebClient conservativeRagClient;


  public String rag(final ChatClient chatClient, final String input) {
    return (chatClient == liberalChatClient ? liberalRagClient : conservativeRagClient)
        .post()
        .uri("/rag")
        .bodyValue(input)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
