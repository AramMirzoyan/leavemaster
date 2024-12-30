package com.leave.master.leavemaster.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserInfoConstant {
  ROLES("roles"),
  ROLE_PREFIX("ROLE_"),
  SUB("sub"),
  NAME("name"),
  PREFERRED_USERNAME("preferred_username"),
  GIVEN_NAME("given_name"),
  FAMILY_NAME("family_name"),
  EMAIL("email"),
  REALM_ACCESS("realm_access"),
  RESOURCE_ACCESS("resource_access");

  private final String name;
}
