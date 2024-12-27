package com.leave.master.leavemaster.security.model;

import com.leave.master.leavemaster.dto.auth.TokenResponseDto;

import java.util.Set;
import java.util.function.BiFunction;

public record SimpleTokenResponseAware(
    BiFunction<KcTokenResponse, Set<String>, TokenResponseDto> biFunc)
    implements TokenResponseAware {

  @Override
  public TokenResponseDto tokenResponse(
      final KcTokenResponse kcTokenResponse, final Set<String> roles) {
    return biFunc.apply(kcTokenResponse, roles);
  }
}
