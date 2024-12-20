package com.leave.master.leavemaster.exceptiondendling;

import org.springframework.http.HttpStatus;

/**
 * Interface representing an error code used in exceptions and error responses. Provides methods to
 * retrieve the error message and the associated HTTP status.
 */
public interface ErrorCode {

  /**
   * Retrieves the error message associated with the error code.
   *
   * @return the error message as a {@link String}.
   */
  String message();

  /**
   * Retrieves the HTTP status associated with the error code.
   *
   * @return the associated {@link HttpStatus}.
   */
  HttpStatus getStatus();
}
