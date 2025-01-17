package com.leave.master.leavemaster.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * A facade for accessing authentication details from the security context.
 *
 * <p>This class provides methods for retrieving the current authenticated user's information, such
 * as their user ID. It is designed for use in security-related components and services.
 */
@Component
public final class AuthenticationFacade {

  /**
   * Retrieves the current authenticated user's ID from the security context.
   *
   * <p>This method accesses the SecurityContextHolder to extract the user ID from the JWT's "sub"
   * claim. It is designed for scenarios where the application's user ID is required for business
   * logic or security purposes.
   *
   * @return the user ID of the currently authenticated user.
   * @throws ClassCastException if the authentication principal is not a JWT.
   */
  public String getCurrentUserId() {
    var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return jwt.getClaimAsString("sub");
  }
}
