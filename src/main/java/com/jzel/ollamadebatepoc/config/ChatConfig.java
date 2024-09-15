package com.jzel.ollamadebatepoc.config;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

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

  private static final String GOP_PLATFORM = encode("""
           2024 Trump-Vance GOP Platform: "Make America Great Again"
           
           Dedication: To America's forgotten men and women.
           
           Preamble: Emphasizes "America First" and a return to common sense.
           
           Core Message: Addressing America's decline with resilience, patriotism, and strong leadership, focusing on border security, economic issues, and national identity.
           
           Key Policies:
           
           Border Security: Complete the Southern Border Wall, launch major deportations, end sanctuary cities.
           Economic Policies: Eliminate inflation, cut taxes, reduce foreign reliance, boost domestic energy.
           Military and Defense: Enhance military strength, develop a missile defense system.
           Social and Cultural: Oppose Critical Race Theory, protect free speech and gun rights, promote patriotic education.
           Healthcare: No cuts to Social Security/Medicare, improve affordability.
           Abortion: Let state laws decide on how to protect the unborn.
           Education: Expand school choice, reduce costs, increase parental control.
           Justice: Restore law and order, support police, combat antisemitism.
           Infrastructure: Rebuild infrastructure, revitalize manufacturing, foster innovation, resist over-regulation.
           
           Conclusion: Aiming to unite America around policies that prioritize national interests and restore pride.
          """,
      UTF_8);

  private static final String DNC_PLATFORM = encode("""
       2024 DNC Platform: "Progress and Prosperity for All"
       
       Land Acknowledgement: Honors Native American ancestral lands, underlining commitments to Tribal Nations and Indigenous stewardship.commitment to supporting Tribal Nations and respecting Indigenous stewardship of these lands.
       
       Preamble: Frames America at a pivotal moment, posing deep questions about freedoms, economic fairness, and unity. Presents a choice between an inclusive vision under Biden-Harris and a divisive alternative offered by Trump.stions about the nature of freedoms, economic fairness, and political unity. It portrays a choice between an inclusive, equitable vision under Biden-Harris and a divisive, retrogressive vision represented by Trump and his allies.
       
       Key Goals and Policies:
       
       Economic Growth: Enhance job creation, support small businesses, ensure the wealthy and corporations pay fair taxes, and tackle economic inequality.
       Healthcare: Expand coverage, reduce prescription costs, and bolster Social Security, Medicare, and Medicaid.
       Abortion: Protect reproductive rights, including access to safe and legal abortion.
       Climate Action: Implement aggressive climate policies, promote clean energy, and invest in green jobs.
       Education: Support universal access from preschool to higher education, emphasizing affordability.
       Civil Rights: Protect voting, reproductive, racial, and LGBTQ+ rights; reform criminal justice.
       Immigration: Advance humane and comprehensive immigration reform, expand pathways to citizenship.
       Global Leadership: Strengthen alliances, promote human rights, and tackle global challenges like climate change.
       
       Conclusion: The platform aims to foster unity around a progressive agenda for a fair and thriving America.
      """, UTF_8);

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
            STR."""
                You are a \{debateSide} debater.
                Provide strong, well-reasoned \{debateSide} arguments and challenge \{oppositeSide} views effectively.
                As an experiment, you are going to face a LLM with the same initial prompt but with the exact opposite regarding the \{debateSide}-\{oppositeSide} spectrum.
                The goal is to provide the user with an engaging factual discussion by also refuting your opponent’s arguments. Normally it is always important to consider both sides, but you should really only consider the \{debateSide} side because the other model will do exactly the same thing as a \{oppositeSide}.

                You can use the following URL-encoded data to support your arguments:
                \{debateSide.equals(CONSERVATIVE) ? GOP_PLATFORM : DNC_PLATFORM}
                """
        )
        .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
        .build();
  }
}
