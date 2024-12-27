package com.leave.master.leavemaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * Configuration class for the LeaveMaster application.
 *
 * <p>Provides beans for configuring JWT authority conversion and HTTP headers.
 *
 * <p>Designed for extension if additional beans or configurations are required.
 */
@Configuration
public class ConfigLeaveMaster {

  /**
   * Configures a {@link JwtGrantedAuthoritiesConverter} bean for converting JWT claims to Spring
   * Security granted authorities.
   *
   * <p>Subclasses can override this method to customize the authority conversion logic.
   *
   * @return a configured {@link JwtGrantedAuthoritiesConverter} instance.
   */
  @Bean
  public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
    return new JwtGrantedAuthoritiesConverter();
  }

  /**
   * Configures a lazy-loaded {@link HttpHeaders} bean with default content type set to {@link
   * MediaType#APPLICATION_FORM_URLENCODED}.
   *
   * <p>Subclasses can override this method to provide custom headers if needed.
   *
   * @return a configured {@link HttpHeaders} instance.
   */
  @Bean
  @Lazy
  public HttpHeaders headers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return headers;
  }
}
