package com.leave.master.leavemaster.security.model;

import java.util.Set;

import com.leave.master.leavemaster.dto.auth.TokenResponseDto;

@FunctionalInterface
public interface TokenResponseAware {

  TokenResponseDto tokenResponse(KcTokenResponse kcTokenResponse, Set<String> roles);
}
