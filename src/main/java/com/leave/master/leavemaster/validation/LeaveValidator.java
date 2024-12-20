package com.leave.master.leavemaster.validation;

/**
 * Validator implementation for validating leave days. Ensures that the input is non-negative and
 * does not exceed the maximum allowed days.
 */
public class LeaveValidator implements Validator<Integer, LeaveValidationContext> {

  /**
   * Validates the given input against the provided {@link LeaveValidationContext}. Ensures that the
   * input is a non-negative integer and does not exceed the maximum allowed days.
   *
   * @param input the number of leave days to validate.
   * @param context the validation context containing rules and constraints.
   * @return true if the input is valid; false otherwise.
   */
  @Override
  public boolean validate(Integer input, LeaveValidationContext context) {
    if (input == null || input < 0) {
      context.invalidInputDetected(input);
      return false;
    }
    if (input > context.maxDaysAllowed()) {
      context.invalidInputDetected(input);
      return false;
    }
    return true;
  }
}
