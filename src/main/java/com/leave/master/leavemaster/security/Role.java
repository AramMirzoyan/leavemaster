package com.leave.master.leavemaster.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Role {
  @JsonProperty("security.role.admin")
  SEC_ROLE_ADMIN(Name.R_SEC_ADMIN, "leavemaster", "Admin role for leavemaster_client",
          "Admin","Administrator role for security"),
  @JsonProperty("security.role.user")
  SEC_ROLE_USER(Name.R_SEC_USER, "leavemaster", "User role for leavemaster_client",
          "User","User role for security");

  private static final Map<String, Role[]> mapping =
      Arrays.stream(Role.values())
          .collect(
              Collectors.groupingBy(
                  role -> role.access,
                  Collectors.collectingAndThen(
                      Collectors.toList(), list -> list.toArray(new Role[0]))));

  private static final Map<String, Role> userRoleTypeMapping =
      Arrays.stream(Role.values())
          .collect(Collectors.toMap(role -> role.userRoleType, Function.identity()));

  private final String roleName;
  private final String access;
  private final String description;
  private final String userRoleType;
  private final String realmDescription;

  Role(
      final String name, final String access, final String description, final String userRoleType, final String realmDescription) {
    this.roleName = name.replaceFirst("ROLE_", "");
    this.access = access;
    this.description = description;
    this.userRoleType = userRoleType;
    this.realmDescription=realmDescription;
    RoleMap.rolesMap.put(roleName, this);
  }

  public static Role findByName(String name) {
    return RoleMap.rolesMap.get(name);
  }

  public static Role findByUserRoleType(final String userRoleType) {
    return userRoleTypeMapping.get(userRoleType);
  }

  public static Role[] findAllByAccess(final String access) {
    return mapping.get(access);
  }

  public static final class Name {
    public static final String R_SEC_ADMIN = "ROLE_security.admin";
    public static final String R_SEC_USER = "ROLE_security.user";
  }

  private record RoleMap() {
    private static final Map<String, Role> rolesMap = new HashMap<>();
  }
}
