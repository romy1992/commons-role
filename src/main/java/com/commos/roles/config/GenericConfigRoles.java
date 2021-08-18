package com.commos.roles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericConfigRoles {
  @Bean
  public AuthEntryPoint authEntryPoint() {
    return new AuthEntryPoint();
  }
}
