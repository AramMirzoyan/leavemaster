package com.leave.master.leavemaster.security.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.leave.master.leavemaster.security.model.ApiClientRequest;
import com.leave.master.leavemaster.security.model.DefaultApiClientRequest;
import com.leave.master.leavemaster.security.model.SimpleTokenResponseAware;
import com.leave.master.leavemaster.security.model.TokenResponseAware;
import com.leave.master.leavemaster.security.properties.ApiClientProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApiClientProviderConfig {

  private final ApiClientProperties apiClientProperties;

  @Bean
  public ApiClientRequest httpHeadersProvider() {
    return new DefaultApiClientRequest(apiClientProperties::requestFromSource);
  }

  @Bean
  public TokenResponseAware tokenResponseAware() {
    return new SimpleTokenResponseAware(apiClientProperties::tokenResponseDto);
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(5_000))
        .setReadTimeout(Duration.ofMillis(15_000))
        .build();
  }
}
