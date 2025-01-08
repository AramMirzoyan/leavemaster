package com.leave.master.leavemaster.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

  public String getCurrentUserId() {
    var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return jwt.getClaimAsString("sub");
  }
}
