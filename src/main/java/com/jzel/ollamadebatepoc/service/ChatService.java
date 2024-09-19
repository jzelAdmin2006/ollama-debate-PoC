package com.jzel.ollamadebatepoc.service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  public static final String CONSERVATIVE = "conservative";
  public static final String LIBERAL = "liberal";
  private static final int CHAT_ID = 1;
  private final ChatClient conservativeChatClient;
  private final PartyPlatformRagService partyPlatformRagService;

  public String chat(final ChatClient chatClient, final String input) {
    final String debateSide = chatClient == conservativeChatClient ? CONSERVATIVE : LIBERAL;
    final String oppositeSide = chatClient == conservativeChatClient ? LIBERAL : CONSERVATIVE;
    return chatClient
        .prompt()
        .user(input)
        .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, CHAT_ID))
        .system(STR."""
                You are a \{debateSide} debater.
                Provide strong, well-reasoned \{debateSide} arguments and challenge \{oppositeSide} views effectively.
                As an experiment, you are going to face a LLM with the same initial prompt but with the exact opposite regarding the \{debateSide}-\{oppositeSide} spectrum.
                The goal is to provide the user with an engaging factual discussion by also refuting your opponent’s arguments. Normally it is always important to consider both sides, but you should really only consider the \{debateSide} side because the other model will do exactly the same thing as a \{oppositeSide}.

                You should use the following position of your party and must include typical slogans and statements from it:
                \{partyPlatformRagService.rag(chatClient, input)}
                """)
        .call()
        .content();
  }
}
