package com.leave.master.leavemaster.security.service;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.security.AuthService;
import com.leave.master.leavemaster.security.apiclient.ApiClient;
import com.leave.master.leavemaster.security.model.TokenResponseAware;
import com.leave.master.leavemaster.service.keycloak.KeycloakService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {
  private final KeycloakService keycloakService;
  private final ApiClient apiClient;
  private final TokenResponseAware tokenResponseAware;

  @Override
  public TokenResponseDto login(final LoginRequestDto source) {
    return tokenResponseAware.tokenResponse(
        apiClient.getTokenResponse(source), keycloakService.getRolesByUserName(source.getEmail()));
  }
}
