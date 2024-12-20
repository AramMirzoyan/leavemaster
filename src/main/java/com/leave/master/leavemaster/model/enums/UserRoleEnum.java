package com.leave.master.leavemaster.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {
  ADMIN("Admin"),
  USER("User");

  private static final Map<String, UserRoleEnum> mapping =
      Arrays.stream(UserRoleEnum.values())
          .collect(Collectors.toMap(it -> it.getName().toLowerCase(), Function.identity()));
  private final String name;

  @JsonCreator
  public static UserRoleEnum ofValue(@NonNull final String value) {
    return mapping.get(value.toLowerCase());
  }
}
