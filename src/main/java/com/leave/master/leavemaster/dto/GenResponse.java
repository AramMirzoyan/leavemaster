package com.leave.master.leavemaster.dto;

import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

import lombok.Builder;
import lombok.Getter;

/**
 * A generic response wrapper class for API responses.
 *
 * @param <T> the type of the response data.
 */
@Builder
@Getter
public class GenResponse<T> {

  private final String status;
  private final String message;
  private final Map<String, String> metadata;
  private final T data;

  /**
   * Creates a success response without any data.
   *
   * @param <T> the type of the response data.
   * @return a {@link GenResponse} instance representing a successful operation.
   */
  public static <T> GenResponse<T> success() {
    return GenResponse.<T>builder()
        .status(GenRespStatus.SUCCESS.getValue())
        .message("Request processed successfuly")
        .metadata(Map.of("timestamp", Instant.now().toString()))
        .build();
  }

  /**
   * Creates a success response with the provided data.
   *
   * @param <T> the type of the response data.
   * @param data the data to include in the response.
   * @return a {@link GenResponse} instance representing a successful operation.
   */
  public static <T> GenResponse<T> success(T data) {
    return GenResponse.<T>builder()
        .status(GenRespStatus.SUCCESS.getValue())
        .message("Request processed successfuly")
        .metadata(Map.of("timestamp", Instant.now().toString()))
        .data(data)
        .build();
  }

  /**
   * Creates a successful {@link GenResponse} with the provided data and message.
   *
   * @param <T> the type of the response data.
   * @param data the data to include in the response.
   * @param message a {@link Supplier} providing the success message for the response.
   * @return a {@link GenResponse} containing the provided data, message, and additional metadata.
   */
  public static <T> GenResponse<T> success(T data, Supplier<String> message) {
    return GenResponse.<T>builder()
        .status(GenRespStatus.SUCCESS.getValue())
        .message(message.get())
        .metadata(Map.of("timestamp", Instant.now().toString()))
        .data(data)
        .build();
  }

  /**
   * Creates an error response with the provided data and a message.
   *
   * @param <T> the type of the response data.
   * @param data the data to include in the response.
   * @param message a supplier providing the error message.
   * @return a {@link GenResponse} instance representing an error.
   */
  public static <T> GenResponse<T> error(final T data, final Supplier<String> message) {
    return GenResponse.<T>builder()
        .status(GenRespStatus.ERROR.getValue())
        .message(message.get())
        .metadata(Map.of("timestamp", Instant.now().toString()))
        .data(data)
        .build();
  }

  /**
   * Creates a validation error response with the specified data.
   *
   * @param <T> the type of the data payload
   * @param data the data payload
   * @return the validation error response with data
   */
  public static <T> GenResponse<T> validation(T data) {
    return GenResponse.<T>builder()
        .status(GenRespStatus.ERROR.getValue())
        .message("Validation error")
        .metadata(Map.of("timestamp", Instant.now().toString()))
        .data(data)
        .build();
  }
}
