package com.jzel.ollamadebatepoc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final ConfigProperties configProperties;

  @Override
  public void configureMessageBroker(final MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(final StompEndpointRegistry registry) {
    registry
        .addEndpoint("/websocket")
        .setAllowedOrigins(configProperties.allowedOrigins().split(";"))
        .withSockJS();
  }

  @ConfigurationProperties(prefix = "security")
  record ConfigProperties(String allowedOrigins) {

  }
}