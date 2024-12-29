package com.leave.master.leavemaster.exceptiondendling;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;

import com.leave.master.leavemaster.dto.erorresponse.ErrorResponse;

import lombok.Getter;

/**
 * Custom exception class for handling method argument errors. Extends the {@link ServiceException}.
 */
@Getter
public class MethodArgumentException extends ServiceException {

  /**
   * Constructs a new exception with the specified HTTP status, message, error code, and a list of
   * detailed error responses.
   *
   * @param status the HTTP status associated with the error.
   * @param message the error message.
   * @param errorCode the error code for this exception.
   * @param errorResponses a list of {@link ErrorResponse} objects providing detailed error
   *     information.
   */
  public MethodArgumentException(
      HttpStatus status, String message, int errorCode, List<ErrorResponse> errorResponses) {
    super(status, message, errorCode, errorResponses);
  }

  /**
   * Constructs a new exception with the specified error code.
   *
   * @param code the {@link ErrorCode} associated with this exception.
   */
  public MethodArgumentException(ErrorCode code) {
    super(code);
  }

  /**
   * Constructs a new exception with the specified error code and a message provider.
   *
   * @param code the {@link ErrorCode} associated with this exception.
   * @param messageProvider a {@link Supplier} for lazily providing the error message.
   */
  public MethodArgumentException(ErrorCode code, Supplier<String> messageProvider) {
    super(code, messageProvider);
  }

  /**
   * Constructs a new exception with the specified error code and detailed error responses.
   *
   * @param code the {@link ErrorCode} associated with this exception.
   * @param responses a list of {@link ErrorResponse} objects providing detailed error information.
   */
  public MethodArgumentException(ErrorCode code, List<ErrorResponse> responses) {
    super(code, responses);
  }
}
