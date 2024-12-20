package com.leave.master.leavemaster.exceptiondendling;

import static com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode.PRECONDITION_FAILED;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.leave.master.leavemaster.dto.GenResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for handling exceptions across all controllers. Logs exceptions and
 * formats error responses to be consistent.
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

  @ExceptionHandler(MethodArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<GenResponse<ErrorResponse>> methodArgumentNotValidException(
      MethodArgumentException ex) {
    List<ErrorDto> errorsFromFields = new ArrayList<>();

    ex.getErrorResponses()
        .forEach(
            errorResponse ->
                errorsFromFields.add(
                    new ErrorDto(errorResponse.getField(), errorResponse.getMessage())));

    return new ResponseEntity<>(
        GenResponse.validation(
            ErrorResponse.builder()
                .status(PRECONDITION_FAILED.getStatus().value())
                .errorDtos(errorsFromFields)
                .build()),
        HttpStatus.valueOf(ex.getErrorCode()));
  }

  /** Represents a structured error response for {@link ServiceException}. */
  @Builder
  @Getter
  public static class ServiceExceptionErrorResponse implements CustomException {
    private Map<String, String> error;
  }

  @Builder
  public record ErrorDto(String field, String message) {}

  @Builder
  public record ErrorResponse(int status, List<ErrorDto> errorDtos) {}
}
