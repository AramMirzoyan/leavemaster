package com.leave.master.leavemaster.security.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KcTokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("expires_in") Integer accessTokenExpiresIn,
    @JsonProperty("refresh_expires_in") Integer refreshTokenExpiresIn,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("session_state") UUID sessionState) {}
