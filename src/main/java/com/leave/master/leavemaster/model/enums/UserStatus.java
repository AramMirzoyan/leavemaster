package com.leave.master.leavemaster.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Enum representing the status of a user. */
@RequiredArgsConstructor
@Getter
public enum UserStatus {

  /**
   * Enum representing the status of a user.
   *
   * <p>This enum provides methods to map string or boolean values to the corresponding {@link
   * UserStatus} instance.
   */
  ACTIVE("activate", true),

  /** Represents an inactive user status. */
  INACTIVE("inactive", false);

  /** A mapping of string values to {@link UserStatus} enums. */
  private static final Map<String, UserStatus> MAPPING =
      Arrays.stream(UserStatus.values())
          .collect(Collectors.toMap(UserStatus::getValue, Function.identity()));

  /** A mapping of boolean values indicating enabled status to {@link UserStatus} enums. */
  private static final Map<Boolean, UserStatus> MAPPING_IS_ENABLED =
      Arrays.stream(UserStatus.values())
          .collect(Collectors.toMap(UserStatus::isEnabled, Function.identity()));

  private final String value;
  private final boolean isEnabled;

  /**
   * Retrieves the {@link UserStatus} corresponding to the given string value.
   *
   * @param value the string representation of the user status.
   * @return the corresponding {@link UserStatus}, or {@code null} if no match is found.
   */
  public static UserStatus ofValue(final String value) {
    return MAPPING.get(value);
  }

  /**
   * Retrieves the {@link UserStatus} corresponding to the given boolean value.
   *
   * @param value the boolean representation of the user status (enabled or not).
   * @return the corresponding {@link UserStatus}, or {@code null} if no match is found.
   */
  public static UserStatus ofEnabledValue(final boolean value) {
    return MAPPING_IS_ENABLED.get(value);
  }
}
