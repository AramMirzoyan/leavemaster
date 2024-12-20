package com.leave.master.leavemaster.validation;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;

public record LeaveValidationContext(int maxDaysAllowed) implements ValidationContext {
  @Override
  public <T> String validationFailedMessage(T input) {
    return (input == null || (input instanceof Integer && (Integer) input < 0))
        ? "Invalid input: number of days off must be a positive integer."
        : String.format(
            "Requested days off (%s) exceed the allowed limit of %d.", input, maxDaysAllowed);
  }

  @Override
  public <T> void invalidInputDetected(T input) {
    throw new ServiceException(
        ServiceErrorCode.LEAVE_VALIDATION, () -> validationFailedMessage(input));
  }
}
