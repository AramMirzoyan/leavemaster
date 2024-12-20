package com.leave.master.leavemaster.interceptors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientAuthHeader {
  AUTH_CLIENT_APP_HEADER("Authorization-Client-App"),
  AUTH_CLIENT_ID_HEADER("Authorization-Client-Id"),

  CLIENT_APP_ADMIN_VALUE("client-app-admin"),
  ;

  private final String value;
}
