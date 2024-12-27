package com.leave.master.leavemaster.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leave.master.leavemaster.security.model.ApiClientRequest;
import com.leave.master.leavemaster.security.model.DefaultApiClientRequest;
import com.leave.master.leavemaster.security.model.SimpleTokenResponseAware;
import com.leave.master.leavemaster.security.model.TokenResponseAware;
import com.leave.master.leavemaster.security.properties.ApiClientProperties;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for providing API client beans.
 *
 * <p>Defines beans for configuring HTTP headers and token response handling used in API client
 * interactions.
 */
@Configuration
@RequiredArgsConstructor
public class ApiClientProviderConfig {

  private final ApiClientProperties apiClientProperties;

  /**
   * Provides a bean for configuring HTTP headers for API client requests.
   *
   * <p>Subclasses can override this method to customize the header configuration logic.
   *
   * @return an {@link ApiClientRequest} instance for HTTP header configuration.
   */
  @Bean
  public ApiClientRequest httpHeadersProvider() {
    return new DefaultApiClientRequest(apiClientProperties::requestFromSource);
  }

  /**
   * Provides a bean for handling token responses for API client interactions.
   *
   * <p>Subclasses can override this method to customize token response handling logic.
   *
   * @return a {@link TokenResponseAware} instance for token response handling.
   */
  @Bean
  public TokenResponseAware tokenResponseAware() {
    return new SimpleTokenResponseAware(apiClientProperties::tokenResponseDto);
  }
}
