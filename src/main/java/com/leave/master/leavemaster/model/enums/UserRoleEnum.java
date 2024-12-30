package com.leave.master.leavemaster.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing user roles in the LeaveMaster application.
 *
 * <p>Each role is associated with a name and provides a utility method to fetch roles by their
 * value.
 */
@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
  /** Admin role with elevated permissions. */
  ADMIN("Admin"),

  /** Standard user role with limited permissions. */
  USER("User");

  /** Mapping of role names to their corresponding {@link UserRoleEnum} values. */
  private static final Map<String, UserRoleEnum> MAPPING =
      Arrays.stream(UserRoleEnum.values())
          .collect(Collectors.toMap(it -> it.getName().toLowerCase(), Function.identity()));

  private final String name;

  /**
   * Retrieves a {@link UserRoleEnum} based on the provided value.
   *
   * @param value the string representation of the role.
   * @return the corresponding {@link UserRoleEnum}.
   * @throws NullPointerException if the provided value is null.
   */
  @JsonCreator
  public static UserRoleEnum ofValue(@NonNull final String value) {
    return MAPPING.get(value.toLowerCase());
  }
}
