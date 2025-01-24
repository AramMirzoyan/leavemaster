package com.leave.master.leavemaster;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leave.master.leavemaster.config.CustomSecurityLoggingFilter;
import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.interceptors.ClientAttributeInterceptor;
import com.leave.master.leavemaster.security.converter.JwtAuthConverter;

import lombok.Getter;

@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties(value = {LeaveMasterSecurityProperties.class})
@WebMvcTest
@Import({LeavemasterApplicationTest.TestConfig.class})
@EnableMethodSecurity(securedEnabled = true)
@Getter
public abstract class LeavemasterApplicationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private ClientAttributeInterceptor clientAttributeInterceptor;

  /**
   * Sets up mock behavior for client attribute interceptor before each test. This method can be
   * overridden to provide additional setup in subclasses.
   */
  @BeforeEach
  void setup() {
    try {
      when(clientAttributeInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sets up mock behavior for client attribute interceptor before each test. This method can be
   * overridden to provide additional setup in subclasses.
   */
  @TestConfiguration
  public static class TestConfig {

    /**
     * Provides the configuration for LeaveMaster security properties.
     *
     * @return a new instance of {@link LeaveMasterSecurityProperties}.
     */
    @Bean
    public LeaveMasterSecurityProperties properties() {
      return new LeaveMasterSecurityProperties();
    }

    /**
     * Provides a JwtAuthConverter bean for testing.
     *
     * @param converter the {@link JwtGrantedAuthoritiesConverter}.
     * @param properties the {@link LeaveMasterSecurityProperties}.
     * @return a new instance of {@link JwtAuthConverter}.
     */
    @Bean
    public JwtAuthConverter jwtAuthConverter(
        JwtGrantedAuthoritiesConverter converter, LeaveMasterSecurityProperties properties) {
      return new JwtAuthConverter(converter, properties);
    }

    /**
     * Provides a JwtGrantedAuthoritiesConverter bean for testing.
     *
     * @return a new instance of {@link JwtGrantedAuthoritiesConverter}.
     */
    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
      return new JwtGrantedAuthoritiesConverter();
    }

    /**
     * Configures the security filter chain for testing purposes.
     *
     * @param http the {@link HttpSecurity} to configure.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(
              auth -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
          .addFilterBefore(
              new CustomSecurityLoggingFilter(), BearerTokenAuthenticationFilter.class);
      return http.build();
    }
  }
}
