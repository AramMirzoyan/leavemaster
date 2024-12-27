package com.leave.master.leavemaster.security.config;

import com.leave.master.leavemaster.security.model.ApiClientRequest;
import com.leave.master.leavemaster.security.model.DefaultApiClientRequest;
import com.leave.master.leavemaster.security.model.SimpleTokenResponseAware;
import com.leave.master.leavemaster.security.model.TokenResponseAware;
import com.leave.master.leavemaster.security.properties.ApiClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

//  @Bean
//  public RestTemplate restTemplate(RestTemplateBuilder builder) {
//    return builder
//        .setConnectTimeout(Duration.ofMillis(5_000))
//        .setReadTimeout(Duration.ofMillis(15_000))
//        .build();
//  }
}
