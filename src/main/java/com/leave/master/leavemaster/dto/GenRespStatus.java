package com.leave.master.leavemaster.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Enum representing the response status for generic responses. */
@Getter
@RequiredArgsConstructor
public enum GenRespStatus {
  SUCCESS("success"),
  ERROR("error");

  private static final Map<String, GenRespStatus> MAPPING =
      Arrays.stream(GenRespStatus.values())
          .collect(Collectors.toMap(GenRespStatus::getValue, Function.identity()));
  private final String value;

  /**
   * Retrieves the {@link GenRespStatus} corresponding to the given value.
   *
   * @param value the string representation of the status.
   * @return the corresponding {@link GenRespStatus}.
   * @throws ServiceException if no matching status is found.
   */
  public static GenRespStatus fromValue(final String value) {
    return Optional.ofNullable(value)
        .map(MAPPING::get)
        .orElseThrow(() -> new ServiceException(ServiceErrorCode.PRECONDITION_FAILED, () -> value));
  }
}
