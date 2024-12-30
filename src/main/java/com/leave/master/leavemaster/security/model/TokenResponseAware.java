package com.leave.master.leavemaster.security.model;

import java.util.Set;

import com.leave.master.leavemaster.dto.auth.TokenResponseDto;

/**
 * Functional interface for processing a token response and mapping it to a {@link
 * TokenResponseDto}.
 *
 * <p>This interface provides a method to convert a {@link KcTokenResponse} and a set of roles into
 * a {@link TokenResponseDto}.
 */
@FunctionalInterface
public interface TokenResponseAware {

  /**
   * Processes the given {@link KcTokenResponse} and roles to generate a {@link TokenResponseDto}.
   *
   * @param kcTokenResponse the {@link KcTokenResponse} containing token details.
   * @param roles a {@link Set} of roles associated with the token.
   * @return a {@link TokenResponseDto} containing the processed token response details.
   */
  TokenResponseDto tokenResponse(KcTokenResponse kcTokenResponse, Set<String> roles);
}
