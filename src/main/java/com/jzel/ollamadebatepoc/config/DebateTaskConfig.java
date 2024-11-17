package com.jzel.ollamadebatepoc.config;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class DebateTaskConfig {

  @Bean
  public Executor taskExecutor() {
    return new ThreadPoolExecutor(5, 5,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>());
  }

}
