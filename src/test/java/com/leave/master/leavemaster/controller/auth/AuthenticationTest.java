package com.leave.master.leavemaster.controller.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.interceptors.ClientAttributeInterceptor;
import com.leave.master.leavemaster.security.AuthService;
import com.leave.master.leavemaster.security.converter.JwtAuthConverter;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(Authentication.class)
@EnableConfigurationProperties(value = {LeaveMasterSecurityProperties.class})
class AuthenticationTest {

  @MockBean private AuthService authService;
  @MockBean private HttpServletRequest httpServletRequest;
  @Autowired private Authentication authentication;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
  @MockBean private ClientAttributeInterceptor clientAttributeInterceptor;

  @BeforeEach
  void setup() {
    try {
      when(clientAttributeInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static Stream<Arguments> testLoginValidationFailedArguments() {
    return Stream.of(
        Arguments.of(
            LoginRequestDto.builder().email("user@exaples.com").build(),
            "password is mandatory",
            "password"),
        Arguments.of(
            LoginRequestDto.builder().password("password").build(), "email is mandatory", "email"));
  }

  @Test
  @DisplayName("Login should success ")
  public void loginSuccess() {
    // given
    LoginRequestDto loginRequestDto =
        LoginRequestDto.builder().email("user@exaples.com").password("password").build();

    TokenResponseDto tokenResponseDto =
        TokenResponseDto.builder()
            .accessToken("testAccessToken")
            .refreshToken("testRefreshToken")
            .build();

    when(httpServletRequest.getAttribute("clientId")).thenReturn("test-client-id");
    when(httpServletRequest.getAttribute("clientSecret")).thenReturn("test-client-secret");
    when(authService.login(any(LoginRequestDto.class))).thenReturn(tokenResponseDto);

    // when
    GenResponse<TokenResponseDto> response =
        authentication.login(loginRequestDto, httpServletRequest);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getData()).isEqualTo(tokenResponseDto);
    assertThat(response.getMessage()).isEqualTo("Login request processed successfully");

    verify(authService).login(any(LoginRequestDto.class));
    verify(authService, times(1))
        .login(
            argThat(
                argument ->
                    argument != null
                        && "user@exaples.com".equals(argument.getEmail())
                        && "password".equals(argument.getPassword())
                        && "test-client-id".equals(argument.getClientId())
                        && "test-client-secret".equals(argument.getClientSecret())));
  }

  @DisplayName("Login should fail with MethodArgumentException when field validation fails")
  @ParameterizedTest
  @MethodSource("testLoginValidationFailedArguments")
  public void testLoginValidationFailed(
      LoginRequestDto requestDto, String message, String fieldName) throws Exception {

    mockMvc
        .perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDto)))
        .andExpect(status().isPreconditionFailed())
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Validation error"))
        .andExpect(jsonPath("$.metadata.timestamp").exists())
        .andExpect(jsonPath("$.data[0].field").value(fieldName))
        .andExpect(jsonPath("$.data[0].message").value(message));

    // verify
    verify(authService, never()).login(any(LoginRequestDto.class));
  }

  @TestConfiguration
  public static class TestConfig {
    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
      return new JwtGrantedAuthoritiesConverter();
    }

    @Bean
    public JwtAuthConverter jwtAuthConverter(
        JwtGrantedAuthoritiesConverter converter, LeaveMasterSecurityProperties properties) {
      return new JwtAuthConverter(converter, properties);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(
              auth -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated());
      return http.build();
    }
  }
}
