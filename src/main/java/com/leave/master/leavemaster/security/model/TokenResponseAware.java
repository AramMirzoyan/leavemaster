package com.leave.master.leavemaster.security.model;

import com.leave.master.leavemaster.dto.auth.TokenResponseDto;

import java.util.Set;

@FunctionalInterface
public interface TokenResponseAware {

  TokenResponseDto tokenResponse(KcTokenResponse kcTokenResponse, Set<String> roles);
}
