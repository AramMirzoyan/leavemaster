package com.leave.master.leavemaster.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.leave.master.leavemaster.dto.erorresponse.ErrorResponse;
import com.leave.master.leavemaster.exceptiondendling.MethodArgumentException;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.utils.Logging;

/**
 * Validator for handling and validating body fields in incoming requests.
 *
 * <p>This class checks for errors in {@link BindingResult} and throws appropriate exceptions if
 * validation fails.
 *
 * <p>If subclassing, ensure to maintain the integrity of error handling and message logging.
 */
@Component
public class FieldValidator {

  /**
   * Validates the fields in the request body and checks for errors in the provided {@link
   * BindingResult}.
   *
   * <p>If there are validation errors, logs them, collects the error details, and throws a {@link
   * MethodArgumentException}. Subclasses overriding this method must ensure they handle errors
   * consistently and log messages as appropriate.
   *
   * @param result the {@link BindingResult} object containing the results of validation.
   * @throws MethodArgumentException if the {@link BindingResult} contains errors, with error
   *     details included.
   */
  public void validateBodyField(final BindingResult result) {
    Logging.onWarning(() -> "Start to validate fields if need to handle");
    if (result.hasErrors()) {
      Logging.onInfo(() -> "Fields has errors");
      final List<ErrorResponse> errors = new ArrayList<>();
      result
          .getAllErrors()
          .forEach(
              error ->
                  errors.add(
                      new ErrorResponse(
                          ((FieldError) error).getField(), error.getDefaultMessage())));

      throw new MethodArgumentException(ServiceErrorCode.BAD_REQUEST, errors);
    }
  }
}
