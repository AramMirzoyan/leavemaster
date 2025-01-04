package com.leave.master.leavemaster.controller.auth;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Disabled
class AuthenticationTest {
  //
  //  @MockBean private AuthService authService;
  //  @MockBean private HttpServletRequest httpServletRequest;
  //  @Autowired private Authentication authentication;
  //
  //  //  static Stream<Arguments> testLoginValidationFailedArguments() {
  //  //    return Stream.of(
  //  //        Arguments.of(
  //  //            LoginRequestDto.builder().email("user@exaples.com").build(),
  //  //            "password is mandatory",
  //  //            "password"),
  //  //        Arguments.of(
  //  //            LoginRequestDto.builder().password("password").build(), "email is mandatory",
  //  // "email"));
  //  //  }
  //
  //  @Test
  //  @DisplayName("Login should success ")
  //  public void loginSuccess() {
  //    // given
  //    LoginRequestDto loginRequestDto =
  //        LoginRequestDto.builder().email("user@exaples.com").password("password").build();
  //
  //    TokenResponseDto tokenResponseDto =
  //        TokenResponseDto.builder()
  //            .accessToken("testAccessToken")
  //            .refreshToken("testRefreshToken")
  //            .build();
  //
  //    when(httpServletRequest.getAttribute("clientId")).thenReturn("test-client-id");
  //    when(httpServletRequest.getAttribute("clientSecret")).thenReturn("test-client-secret");
  //    when(authService.login(any(LoginRequestDto.class))).thenReturn(tokenResponseDto);
  //
  //    // when
  //    GenResponse<TokenResponseDto> response =
  //        authentication.login(loginRequestDto, httpServletRequest);
  //
  //    // then
  //    assertThat(response).isNotNull();
  //    assertThat(response.getData()).isEqualTo(tokenResponseDto);
  //    assertThat(response.getMessage()).isEqualTo("Login request processed successfully");
  //
  //    verify(authService).login(any(LoginRequestDto.class));
  //    verify(authService, times(1))
  //        .login(
  //            argThat(
  //                argument ->
  //                    argument != null
  //                        && "user@exaples.com".equals(argument.getEmail())
  //                        && "password".equals(argument.getPassword())
  //                        && "test-client-id".equals(argument.getClientId())
  //                        && "test-client-secret".equals(argument.getClientSecret())));
  //  }

  //  @DisplayName("Login should fail with MethodArgumentException when field validation fails")
  //  @ParameterizedTest
  //  @MethodSource("testLoginValidationFailedArguments")
  //  public void testLoginValidationFailed(LoginRequestDto requestDto, String message, String
  // field) {
  //    // given
  //
  //    FieldError fieldError = new FieldError("loginRequestDto", field, message);
  //    when(bindingResult.hasErrors()).thenReturn(true);
  //    when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
  //
  //    doAnswer(
  //            invocation -> {
  //              BindingResult result = invocation.getArgument(0);
  //              if (result.hasErrors()) {
  //                List<ErrorResponse> errors =
  //                    result.getAllErrors().stream()
  //                        .map(
  //                            error ->
  //                                new ErrorResponse(
  //                                    ((FieldError) error).getField(), error.getDefaultMessage()))
  //                        .toList();
  //                throw new MethodArgumentException(ServiceErrorCode.BAD_REQUEST, errors);
  //              }
  //              return null;
  //            })
  //        .when(fieldValidator)
  //        .validateBodyField(bindingResult);
  //
  //    // when
  //    MethodArgumentException exception =
  //        assertThrows(
  //            MethodArgumentException.class,
  //            () -> authentication.login(requestDto, httpServletRequest));
  //
  //    // then
  //    assertNotNull(exception);
  //    assertTrue(
  //        exception.getErrorResponses().stream()
  //            .anyMatch(
  //                error -> field.equals(error.getField()) && message.equals(error.getMessage())));
  //
  //    verify(bindingResult, times(1)).hasErrors();
  //    verify(bindingResult, times(1)).getAllErrors();
  //  }
}
