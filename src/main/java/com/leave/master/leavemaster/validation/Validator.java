package com.leave.master.leavemaster.validation;

/**
 * Interface for validating input data against a specific validation context.
 *
 * @param <T> the type of the input to be validated.
 * @param <E> the type of the validation context extending {@link ValidationContext}.
 */
public interface Validator<T, E extends ValidationContext> {

  /**
   * Validates the given input against the provided validation context.
   *
   * @param input the input data to validate.
   * @param context the validation context containing rules and constraints.
   * @return true if the input is valid; false otherwise.
   */
  boolean validate(T input, E context);
}
