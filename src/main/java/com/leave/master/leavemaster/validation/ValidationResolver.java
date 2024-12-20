package com.leave.master.leavemaster.validation;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;

/**
 * Utility class for resolving validation logic. Provides methods to validate input using specified
 * validators and contexts.
 */
public class ValidationResolver {

  /**
   * Validates the given input using the specified {@link Validator} and {@link
   * LeaveValidationContext}. If the input is invalid, a {@link ServiceException} is thrown.
   *
   * @param <T> the type of the input to validate.
   * @param <E> the type of the validation context.
   * @param input the input to validate.
   * @param validator the {@link Validator} to use for validation.
   * @param context the {@link LeaveValidationContext} providing validation rules and messages.
   * @throws ServiceException if the input is invalid according to the validator and context.
   */
  public static <T, E extends LeaveValidationContext> void validateInput(
      T input, Validator<T, E> validator, E context) {
    boolean isValid = validator.validate(input, context);
    if (!isValid) {
      throw new ServiceException(
          ServiceErrorCode.LEAVE_VALIDATION, () -> context.validationFailedMessage(input));
    }
  }
}
