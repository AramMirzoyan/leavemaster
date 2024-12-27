package com.leave.master.leavemaster.utils;

import static lombok.AccessLevel.PRIVATE;

import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for logging messages with different levels of severity.
 *
 * <p>Provides methods for logging informational, warning, and error messages. Supports logging with
 * or without content formatting.
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class Logging {
  /**
   * Logs an informational message.
   *
   * @param message the supplier providing the message to log.
   * @throws IllegalArgumentException if the message is null or empty.
   */
  public static void onInfo(@NonNull final Supplier<String> message) {
    onSuccess(message);
  }

  /**
   * Logs a success message at the informational level.
   *
   * @param message the supplier providing the message to log.
   * @throws IllegalArgumentException if the message is null or empty.
   */
  public static void onSuccess(@NonNull final Supplier<String> message) {
    Assert.notNull(message.get(), "Message can not be empty!!");
    log.info(message.get());
  }

  /**
   * Logs a success message with associated content at the informational level.
   *
   * @param <T> the type of the content.
   * @param content the content associated with the message.
   * @param message the supplier providing the message to log.
   * @throws NullPointerException if the content or message is null.
   * @throws IllegalArgumentException if the message is empty.
   */
  public static <T> void onSuccess(final T content, @NonNull final Supplier<String> message) {
    Objects.requireNonNull(content);
    Assert.notNull(message.get(), "Message can not be empty!!");
    var messageInfo = message.get().formatted(content);
    log.info(messageInfo);
  }

  /**
   * Logs an error message.
   *
   * @param message the supplier providing the message to log.
   * @throws IllegalArgumentException if the message is null or empty.
   */
  public static void onFailed(@NonNull final Supplier<String> message) {
    Assert.notNull(message.get(), "Message can not be empty!!");
    log.error(message.get());
  }

  /**
   * Logs an error message with associated content.
   *
   * @param <T> the type of the content.
   * @param content the content associated with the message.
   * @param message the supplier providing the message to log.
   * @throws NullPointerException if the content or message is null.
   * @throws IllegalArgumentException if the message is empty.
   */
  public static <T> void onFailed(@NonNull final T content, final Supplier<String> message) {
    Objects.requireNonNull(content);
    Assert.notNull(message.get(), "Message can not be empty!!");
    var messageInfo = message.get().formatted(content);
    log.error(messageInfo);
  }

  /**
   * Logs a warning message.
   *
   * @param message the supplier providing the message to log.
   * @throws IllegalArgumentException if the message is null or empty.
   */
  public static void onWarning(@NonNull final Supplier<String> message) {
    Assert.notNull(message.get(), "Message can not be empty!!");
    log.warn(message.get());
  }

  /**
   * Logs a warning message with associated content.
   *
   * @param <T> the type of the content.
   * @param content the content associated with the message.
   * @param message the supplier providing the message to log.
   * @throws NullPointerException if the content or message is null.
   * @throws IllegalArgumentException if the message is empty.
   */
  public static <T> void onWarning(@NonNull final T content, final Supplier<String> message) {
    Objects.requireNonNull(content);
    Assert.notNull(message.get(), "Message can not be empty!!");
    var messageInfo = message.get().formatted(content);
    log.warn(messageInfo);
  }
}
