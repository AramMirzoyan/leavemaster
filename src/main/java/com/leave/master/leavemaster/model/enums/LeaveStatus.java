package com.leave.master.leavemaster.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Enum representing the status of a leave request. */
@RequiredArgsConstructor
@Getter
public enum LeaveStatus {
  PENDING("pending"),
  APPROVED("approved"),
  REJECTED("rejected");

  /** A mapping of string values to {@link LeaveStatus} enums. */
  private static final Map<String, LeaveStatus> MAPPING =
      Arrays.stream(LeaveStatus.values())
          .collect(Collectors.toMap(LeaveStatus::getValue, Function.identity()));

  private final String value;

  /**
   * Retrieves the {@link LeaveStatus} corresponding to the given string value.
   *
   * @param value the string representation of the leave status.
   * @return the corresponding {@link LeaveStatus}, or {@code null} if no match is found.
   */
  public static LeaveStatus ofValue(final String value) {
    return MAPPING.get(value);
  }
}
