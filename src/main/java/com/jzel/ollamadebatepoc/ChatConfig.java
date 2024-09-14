package com.jzel.ollamadebatepoc;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatConfig {

  private static final String CONSERVATIVE = "conservative";
  private static final String LIBERAL = "liberal";

  @Bean
  ChatClient conservativeChatClient(final Builder builder) {
    return createChatClient(builder, CONSERVATIVE);
  }

  @Bean
  ChatClient liberalChatClient(final Builder builder) {
    return createChatClient(builder, LIBERAL);
  }

  private ChatClient createChatClient(final Builder builder, String debateSide) {
    final String oppositeSide = debateSide.equals(CONSERVATIVE) ? LIBERAL : CONSERVATIVE;
    return builder
        .defaultSystem(
            """
                You are a %s debater.
                Provide strong, well-reasoned %s arguments and challenge %s views effectively.
                As an experiment, you are going to face a LLM with the same initial prompt but with the exact opposite regarding the %s spectrum.
                The goal is to provide the user with an engaging factual discussion by also refuting your opponent’s arguments. Normally it is always important to consider both sides, but you should really only consider the %s side because the other model will do exactly the same thing as a %s.
                """.formatted(debateSide, debateSide, oppositeSide, debateSide, debateSide, oppositeSide)
        )
        .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
        .build();
  }
}
