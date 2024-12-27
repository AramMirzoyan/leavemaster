package com.leave.master.leavemaster.model.enums;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Enum representing different types of leave. */
@RequiredArgsConstructor
@Getter
public enum LeaveType {
  ANNUAL_LEAVE,
  SICK_LEAVE,
  UNPAID_LEAVE;

  /** A mapping of string values to {@link LeaveType} enums. */
  private static final Map<String, LeaveType> MAPPING =
      Arrays.stream(LeaveType.values())
          .collect(Collectors.toMap(LeaveType::getName, Function.identity()));

  private String name;

  /**
   * Retrieves the {@link LeaveType} corresponding to the given string value.
   *
   * @param leaveType the string representation of the leave type.
   * @return the corresponding {@link LeaveType}.
   * @throws ServiceException if no matching leave type is found.
   */
  public static LeaveType ofValue(final String leaveType) {
    return Optional.ofNullable(leaveType)
        .map(MAPPING::get)
        .orElseThrow(
            () ->
                new ServiceException(
                    ServiceErrorCode.BAD_REQUEST, () -> "not found provided type"));
  }
}
