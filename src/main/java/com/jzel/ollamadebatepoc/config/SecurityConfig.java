package com.jzel.ollamadebatepoc.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http
        .cors(withDefaults())
        .csrf((csrf) -> csrf.ignoringRequestMatchers("/**"))
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(List.of("content-type", "x-requested-with", "authorization"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
