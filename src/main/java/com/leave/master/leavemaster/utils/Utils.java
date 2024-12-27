package com.leave.master.leavemaster.utils;

import lombok.NoArgsConstructor;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

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
