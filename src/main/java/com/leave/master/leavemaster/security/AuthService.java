package com.leave.master.leavemaster.security;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;

/**
 * Service interface for handling authentication operations.
 *
 * <p>Provides methods for user login and token management.
 */
public interface AuthService {

  /**
   * Authenticates a user and retrieves a token response.
   *
   * @param source the {@link LoginRequestDto} containing login credentials.
   * @return a {@link TokenResponseDto} containing the authentication token and related details.
   */
  TokenResponseDto login(LoginRequestDto source);
}
