package com.leave.master.leavemaster.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.security.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for handling authentication-related requests.
 *
 * <p>Provides endpoints for login and other authentication-related operations.
 */
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "${auth.tag.name}", description = "${auth.tag.dsc}")
public class Authentication {
  private final AuthService authService;

  /**
   * Handles the login request.
   *
   * <p>Processes login credentials and generates an authentication token.
   *
   * @param source the {@link LoginRequestDto} containing login details.
   * @param request the {@link HttpServletRequest} containing additional attributes.
   * @return a {@link GenResponse} containing the {@link TokenResponseDto} with the authentication
   *     token.
   */
  @PostMapping("/login")
  @Operation(
      summary = "${auth.login.summary}",
      description = "${auth.login.dsc}",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = @Content(schema = @Schema(implementation = LoginRequestDto.class)),
              useParameterTypeSchema = true,
              required = true),
      method = "${auth.method}")
  public GenResponse<TokenResponseDto> login(
      @RequestBody @Valid final LoginRequestDto source, final HttpServletRequest request) {
    return GenResponse.success(
        authService.login(
            source.toBuilder()
                .clientId((String) request.getAttribute("clientId"))
                .clientSecret((String) request.getAttribute("clientSecret"))
                .build()),
        () -> "Login request processed successfully");
  }
}
