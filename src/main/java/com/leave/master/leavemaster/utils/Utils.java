package com.leave.master.leavemaster.utils;

import static lombok.AccessLevel.PRIVATE;

import java.util.function.Supplier;

import lombok.NoArgsConstructor;

/**
 * Utility class providing common methods for validation and logging.
 *
 * <p>This class is not meant to be instantiated and contains only static utility methods.
 */
@NoArgsConstructor(access = PRIVATE)
public class Utils {
  /**
   * Checks if the provided content is {@code null} and logs a warning if it is.
   *
   * @param <T> the type of the content to check.
   * @param content the content to validate for nullity.
   * @param message a {@link Supplier} providing the warning message to log if the content is {@code
   *     null}.
   */
  public static <T> void isNull(final T content, final Supplier<String> message) {
    if (content == null) {
      Logging.onWarning(message);
    }
  }
}
