package com.leave.master.leavemaster.utils;

import static lombok.AccessLevel.PRIVATE;

import java.util.function.Supplier;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class Utils {

  public static <T> boolean isNull(final T content, final Supplier<String> message) {
    if (content == null) {
      Logging.onWarning(message);
      return true;
    }
    return false;
  }
}
