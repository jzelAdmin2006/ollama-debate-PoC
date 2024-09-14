package com.jzel.ollamadebatepoc;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfig {

  @Bean
  ChatClient chatClient(final Builder builder) {
    return builder.build();
  }
}
