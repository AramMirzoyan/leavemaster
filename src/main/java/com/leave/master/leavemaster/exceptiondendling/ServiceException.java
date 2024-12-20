package com.leave.master.leavemaster.exceptiondendling;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;

import com.leave.master.leavemaster.dto.erorresponse.ErrorResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Custom exception class to handle service-related errors. Includes HTTP status, error message,
 * error code, and optional error details.
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ServiceException extends RuntimeException {

  private final HttpStatus status;
  private final String message;
  private final int errorCode;
  private final List<ErrorResponse> errorResponses;

  /**
   * Constructs a ServiceException using an {@link ErrorCode}.
   *
   * @param code the error code defining the HTTP status and message.
   */
  public ServiceException(final ErrorCode code) {
    this.status = code.getStatus();
    this.message = code.message();
    this.errorCode = code.getStatus().value();
    this.errorResponses = null;
  }

  /**
   * Constructs a ServiceException using an {@link ErrorCode} and a message provider. The message
   * provider allows appending additional details to the default message.
   *
   * @param code the error code defining the HTTP status and message.
   * @param messageProvider the supplier providing additional details to append to the message.
   */
  public ServiceException(final ErrorCode code, final Supplier<String> messageProvider) {
    this.status = code.getStatus();
    this.message = code.message().concat(" ").concat(messageProvider.get());
    this.errorCode = code.getStatus().value();
    this.errorResponses = null;
  }

  /**
   * Constructs a ServiceException using an {@link ErrorCode} and a list of error responses. The
   * error responses provide additional context about the exception.
   *
   * @param code the error code defining the HTTP status and message.
   * @param responses the list of {@link ErrorResponse} providing additional error details.
   */
  public ServiceException(final ErrorCode code, final List<ErrorResponse> responses) {
    this.status = code.getStatus();
    this.message = code.message();
    this.errorCode = code.getStatus().value();
    this.errorResponses = responses;
  }
}
