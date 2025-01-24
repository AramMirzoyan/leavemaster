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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leave.master.leavemaster.config.CustomSecurityLoggingFilter;
import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.containers.LeaveMasterAuthToken;
import com.leave.master.leavemaster.interceptors.ClientAttributeInterceptor;
import com.leave.master.leavemaster.security.converter.JwtAuthConverter;

@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties(value = {LeaveMasterSecurityProperties.class})
@WebMvcTest
@Import({LeavemasterApplicationTests.TestConfig.class})
public class LeavemasterApplicationTests {

  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;
  @MockBean protected ClientAttributeInterceptor clientAttributeInterceptor;
  protected LeaveMasterAuthToken leaveMasterAuthToken;
  @MockBean private JwtAuthConverter jwtAuthConverter;

  @BeforeEach
  void setup() {
    leaveMasterAuthToken = new LeaveMasterAuthToken();
    try {
      when(clientAttributeInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @TestConfiguration
  public static class TestConfig {

    @Bean
    public LeaveMasterSecurityProperties properties() {
      return new LeaveMasterSecurityProperties();
    }

    @Bean
    public JwtAuthConverter jwtAuthConverter(
        JwtGrantedAuthoritiesConverter converter, LeaveMasterSecurityProperties properties) {
      return new JwtAuthConverter(converter, properties);
    }

    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
      return new JwtGrantedAuthoritiesConverter();
    }

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
