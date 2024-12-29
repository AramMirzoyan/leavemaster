package com.leave.master.leavemaster.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * Enumeration representing the various roles in the LeaveMaster application.
 *
 * <p>Each role is associated with a name, access level, description, user role type, and realm
 * description.
 */
@Getter
public enum Role {
  @JsonProperty("security.role.admin")
  SEC_ROLE_ADMIN(
      Name.R_SEC_ADMIN,
      "leavemaster",
      "Admin role for leavemaster_client",
      "Admin",
      "Administrator role for security"),
  @JsonProperty("security.role.user")
  SEC_ROLE_USER(
      Name.R_SEC_USER,
      "leavemaster",
      "User role for leavemaster_client",
      "User",
      "User role for security");

  /** Mapping of access levels to their corresponding roles. */
  private static final Map<String, Role[]> MAPPING =
      Arrays.stream(Role.values())
          .collect(
              Collectors.groupingBy(
                  role -> role.access,
                  Collectors.collectingAndThen(
                      Collectors.toList(), list -> list.toArray(new Role[0]))));

  /** Mapping of user role types to their corresponding roles. */
  private static final Map<String, Role> USER_ROLE_TYPE_MAPPING =
      Arrays.stream(Role.values())
          .collect(Collectors.toMap(role -> role.userRoleType, Function.identity()));

  private final String roleName;
  private final String access;
  private final String description;
  private final String userRoleType;
  private final String realmDescription;

  Role(
      final String name,
      final String access,
      final String description,
      final String userRoleType,
      final String realmDescription) {
    this.roleName = name.replaceFirst("ROLE_", "");
    this.access = access;
    this.description = description;
    this.userRoleType = userRoleType;
    this.realmDescription = realmDescription;
    RoleMap.ROLES_MAP.put(roleName, this);
  }

  /**
   * Finds a role by its name.
   *
   * @param name the name of the role.
   * @return the {@link Role} corresponding to the name, or {@code null} if not found.
   */
  public static Role findByName(String name) {
    return RoleMap.ROLES_MAP.get(name);
  }

  /**
   * Finds a role by its user role type.
   *
   * @param userRoleType the user role type to search for.
   * @return the {@link Role} corresponding to the user role type, or {@code null} if not found.
   */
  public static Role findByUserRoleType(final String userRoleType) {
    return USER_ROLE_TYPE_MAPPING.get(userRoleType);
  }

  /**
   * Finds all roles associated with a specific access level.
   *
   * @param access the access level.
   * @return an array of {@link Role} associated with the access level.
   */
  public static Role[] findAllByAccess(final String access) {
    return MAPPING.get(access);
  }

  /** Constants for role names. */
  public static final class Name {
    public static final String R_SEC_ADMIN = "ROLE_security.admin";
    public static final String R_SEC_USER = "ROLE_security.user";
  }

  /** Internal mapping of role names to their {@link Role} instances. */
  private record RoleMap() {
    private static final Map<String, Role> ROLES_MAP = new HashMap<>();
  }
}
