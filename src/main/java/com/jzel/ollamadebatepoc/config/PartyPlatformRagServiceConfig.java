package com.jzel.ollamadebatepoc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@ConfigurationProperties(prefix = "politics.platform.ragservice")
public record PartyPlatformRagServiceConfig(String liberal, String conservative) {

  @Bean
  public WebClient liberalRagClient() {
    return createWebClient(liberal);
  }

  @Bean
  public WebClient conservativeRagClient() {
    return createWebClient(conservative);
  }

  private WebClient createWebClient(final String baseUrl) {
    return WebClient
        .builder()
        .baseUrl(baseUrl)
        .build();
  }
}
