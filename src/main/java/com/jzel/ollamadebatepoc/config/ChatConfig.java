package com.jzel.ollamadebatepoc.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatConfig {

  @Bean
  ChatClient conservativeChatClient(final Builder builder) {
    return createChatClient(builder);
  }

  @Bean
  ChatClient liberalChatClient(final Builder builder) {
    return createChatClient(builder);
  }

  private ChatClient createChatClient(final Builder builder) {
    return builder
        .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
        .build();
  }
}
