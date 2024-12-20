package com.leave.master.leavemaster.validation;

/**
 * Interface representing a validation context for processing input data. Provides default methods
 * for handling validation failure scenarios and invalid inputs.
 */
public interface ValidationContext {

  /**
   * Generates a default validation failure message for the given input.
   *
   * @param <T> the type of the input data.
   * @param input the input data that failed validation.
   * @return a default validation failure message.
   */
  default <T> String validationFailedMessage(T input) {
    return "Validation Failed";
  }

  /**
   * Handles the detection of invalid input data.
   *
   * @param <T> the type of the input data.
   * @param input the invalid input data.
   */
  default <T> void invalidInputDetected(T input) {}
}
