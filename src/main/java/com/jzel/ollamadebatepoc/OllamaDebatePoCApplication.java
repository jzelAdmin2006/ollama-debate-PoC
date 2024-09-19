package com.jzel.ollamadebatepoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.jzel.ollamadebatepoc.config")
public class OllamaDebatePoCApplication {

  public static void main(final String[] args) {
    SpringApplication.run(OllamaDebatePoCApplication.class, args);
  }

}
