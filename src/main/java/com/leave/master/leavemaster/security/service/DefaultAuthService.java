package com.leave.master.leavemaster.security.service;

import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.security.AuthService;
import com.leave.master.leavemaster.security.apiclient.ApiClient;
import com.leave.master.leavemaster.security.model.TokenResponseAware;
import com.leave.master.leavemaster.service.keycloak.KeycloakService;

import lombok.RequiredArgsConstructor;

/**
 * Default implementation of the {@link AuthService} interface.
 *
 * <p>This service handles user authentication by interacting with external systems such as Keycloak
 * and API clients.
 *
 * <p>If subclassing, ensure that the overridden {@code login} method maintains consistency in
 * authentication logic and handles token generation securely.
 */
@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {
  private final KeycloakService keycloakService;
  private final ApiClient apiClient;
  private final TokenResponseAware tokenResponseAware;

  /**
   * Logs in a user based on the provided credentials and returns the authentication token response.
   *
   * <p>This method interacts with the {@link ApiClient} to retrieve token responses and with the
   * {@link KeycloakService} to fetch user roles.
   *
   * @param source the {@link LoginRequestDto} containing the user's login credentials.
   * @return a {@link TokenResponseDto} containing the token details and user roles.
   */
  @Override
  public TokenResponseDto login(final LoginRequestDto source) {
    return tokenResponseAware.tokenResponse(
        apiClient.getTokenResponse(source), keycloakService.getRolesByUserName(source.getEmail()));
  }
}
