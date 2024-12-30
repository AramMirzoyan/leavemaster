package com.leave.master.leavemaster.interceptors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Enum representing various client authentication headers and their associated values. */
@Getter
@RequiredArgsConstructor
public enum ClientAuthHeader {

  /** Header for client application authorization. */
  AUTH_CLIENT_APP_HEADER("Authorization-Client-App"),

  /** Header for client ID authorization. */
  AUTH_CLIENT_ID_HEADER("Authorization-Client-Id"),

  /** Value representing the client application admin. */
  CLIENT_APP_ADMIN_VALUE("client-app-admin");

  /** The string value associated with the header or value type. */
  private final String value;
}
