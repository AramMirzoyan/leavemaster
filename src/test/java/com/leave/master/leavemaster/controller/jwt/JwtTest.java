package com.leave.master.leavemaster.controller.jwt;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.leave.master.leavemaster.containers.LeaveMasterAuthToken;

class JwtTest {
  private LeaveMasterAuthToken authToken;

  @BeforeEach
  void init() {
    authToken = new LeaveMasterAuthToken();
  }

  @DisplayName("Should get access_token successfully")
  @Test
  void getSuccessJtwAccessToken() throws URISyntaxException {
    // given
    String accessToken = authToken.authToken("admin", "admin");

    // then
    Assertions.assertNotNull(accessToken);
  }
}
