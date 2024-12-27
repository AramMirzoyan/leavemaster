package com.leave.master.leavemaster.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder(toBuilder = true)
@ToString
public class TokenResponseDto {

  private final String userId;
  private final String username;
  private final String accessToken;
  private final Integer accessTokenExpiresIn;
  private final String refreshToken;
  private final Integer refreshTokenExpiresIn;
  private Set<String> roles;
}
