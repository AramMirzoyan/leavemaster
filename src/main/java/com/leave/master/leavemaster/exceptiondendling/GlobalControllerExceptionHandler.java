package com.leave.master.leavemaster.exceptiondendling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.erorresponse.ErrorResponse;
import com.leave.master.leavemaster.utils.Logging;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for handling exceptions across all controllers. Designed for extension
 * to allow customization of error handling behavior.
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  /**
   * Handles {@link ServiceException} and formats a structured error response.
   *
   * @param ex the {@link ServiceException} being handled.
   * @return a {@link ResponseEntity} containing the error details.
   */
  @ExceptionHandler(value = {ServiceException.class})
  public ResponseEntity<GenResponse<CustomException>> serviceException(ServiceException ex) {
    Map<String, String> error =
        Map.of(
            "Code",
            String.valueOf(ex.getErrorCode()),
            "Details",
            String.join(" ", ex.getMessage()));

    return new ResponseEntity<>(
        GenResponse.error(
            ServiceExceptionErrorResponse.builder().error(error).build(), ex::getMessage),
        HttpStatus.valueOf(ex.getErrorCode()));
  }

  /**
   * Handles {@link MethodArgumentException} and returns a validation error response.
   *
   * @param ex the {@link MethodArgumentException} containing validation errors.
   * @return a {@link ResponseEntity} with validation error details.
   */
  @ExceptionHandler(MethodArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<GenResponse<List<ErrorResponse>>> methodArgumentNotValidException(
      MethodArgumentException ex) {
    return new ResponseEntity<>(
        GenResponse.validation(ex.getErrorResponses()), HttpStatus.valueOf(ex.getErrorCode()));
  }

  /**
   * Handles {@link NotAuthorizedException} and maps it to an error response with HTTP status
   * UNAUTHORIZED (401).
   *
   * @param ex the {@link NotAuthorizedException} that occurred.
   * @return a {@link ResponseEntity} containing the error response.
   */
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = {NotAuthorizedException.class})
  public ResponseEntity<GenResponse<String>> accessDenied(NotAuthorizedException ex) {
    Logging.onFailed(ex, ex::getMessage);
    return new ResponseEntity<>(
        GenResponse.error("Access denied", ex::getMessage), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles {@link HttpClientErrorException.Unauthorized} and maps it to an error response with
   * HTTP status UNAUTHORIZED (401).
   *
   * @param ex the {@link HttpClientErrorException.Unauthorized} exception.
   * @return a {@link ResponseEntity} containing the error response.
   */
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = {HttpClientErrorException.Unauthorized.class})
  public ResponseEntity<GenResponse<String>> accessDeniedUnauthorized(
      HttpClientErrorException.Unauthorized ex) {
    Logging.onFailed(ex, ex::getMessage);
    return new ResponseEntity<>(
        GenResponse.error("Invalid user credentials", ex::getMessage), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles {@link AccessDeniedException} and maps it to an error response with HTTP status
   * FORBIDDEN (403).
   *
   * @param ex the {@link AccessDeniedException} that occurred.
   * @return a {@link ResponseEntity} containing the error response.
   */
  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<GenResponse<String>> accessDeniedException(AccessDeniedException ex) {
    Logging.onFailed(ex, ex::getMessage);
    return new ResponseEntity<>(
        GenResponse.error(ex.getMessage(), ex::getMessage), HttpStatus.FORBIDDEN);
  }

  /**
   * Handles {@link InvalidBearerTokenException} and maps it to an error response with HTTP status
   * FORBIDDEN (403).
   *
   * @param ex the {@link InvalidBearerTokenException} that occurred.
   * @return a {@link ResponseEntity} containing the error response.
   */
  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(InvalidBearerTokenException.class)
  public ResponseEntity<GenResponse<String>> invalidBearerTokenException(
      InvalidBearerTokenException ex) {
    Logging.onFailed(ex, ex::getMessage);
    return new ResponseEntity<>(
        GenResponse.error(ex.getMessage(), ex::getMessage), HttpStatus.FORBIDDEN);
  }

  /**
   * Handles {@link BadRequestException} and maps it to an error response with HTTP status FORBIDDEN
   * (403).
   *
   * @param ex the {@link BadRequestException} that occurred.
   * @return a {@link ResponseEntity} containing the error response.
   */
  @ResponseBody
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<GenResponse<String>> jwtValidationException(BadRequestException ex) {
    Logging.onFailed(ex, ex::getMessage);
    return new ResponseEntity<>(
        GenResponse.error(ex.getMessage(), ex::getMessage), HttpStatus.FORBIDDEN);
  }

  /**
   * Handles validation exceptions such as {@link BindException}, {@link
   * MethodArgumentNotValidException}, and {@link MissingServletRequestPartException}, returning a
   * validation error response.
   *
   * @param ex the exception containing validation errors.
   * @return a {@link ResponseEntity} with the validation error details.
   */
  @ResponseBody
  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
  @ExceptionHandler({
    BindException.class,
    MethodArgumentNotValidException.class,
    MissingServletRequestPartException.class
  })
  public ResponseEntity<GenResponse<List<ErrorResponse>>> methodArgumentNotValidException(
      BindException ex) {
    final List<ErrorResponse> errors = new ArrayList<>();
    ex.getAllErrors()
        .forEach(
            error ->
                errors.add(
                    new ErrorResponse(((FieldError) error).getField(), error.getDefaultMessage())));

    return new ResponseEntity<>(GenResponse.validation(errors), HttpStatus.PRECONDITION_FAILED);
  }

  /** Represents a structured error response for {@link ServiceException}. */
  @Builder
  @Getter
  public static class ServiceExceptionErrorResponse implements CustomException {
    private Map<String, String> error;
  }
}
