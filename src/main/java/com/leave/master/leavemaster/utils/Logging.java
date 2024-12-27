package com.leave.master.leavemaster.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class Logging {

  public static void onInfo(@NonNull final Supplier<String> message) {
    onSuccess(message);
  }

  public static void onSuccess(@NonNull final Supplier<String> message) {
    Assert.notNull(message.get(), "Message can not be empty!!");
    log.info(message.get());
  }

  public static <T> void onSuccess(final T content, @NonNull final Supplier<String> message) {
    Objects.requireNonNull(content);
    Assert.notNull(message.get(), "Message can not be empty!!");
    var messageInfo = message.get().formatted(content);
    log.info(messageInfo);
  }

  public static void onFailed(@NonNull final Supplier<String> message) {
    Assert.notNull(message.get(), "Message can not be empty!!");
    log.error(message.get());
  }

  public static <T> void onFailed(@NonNull final T content, final Supplier<String> message) {
    Objects.requireNonNull(content);
    Assert.notNull(message.get(), "Message can not be empty!!");
    var messageInfo = message.get().formatted(content);
    log.error(messageInfo);
  }

  public static void onWarning(@NonNull final Supplier<String> message) {
    Assert.notNull(message.get(), "Message can not be empty!!");
    log.warn(message.get());
  }

  public static <T> void onWarning(@NonNull final T content, final Supplier<String> message) {
    Objects.requireNonNull(content);
    Assert.notNull(message.get(), "Message can not be empty!!");
    var messageInfo = message.get().formatted(content);
    log.warn(messageInfo);
  }
}
