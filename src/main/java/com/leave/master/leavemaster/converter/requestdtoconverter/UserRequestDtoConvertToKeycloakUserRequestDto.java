package com.leave.master.leavemaster.converter.requestdtoconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;

public class UserRequestDtoConvertToKeycloakUserRequestDto
    implements Converter<UserRequestDto, KeycloakUserRequestDto> {
  @Override
  public boolean support(final Class<?> sourceType, final Class<?> targetType) {
    return sourceType == UserRequestDto.class && targetType == KeycloakUserRequestDto.class;
  }

  @Override
  public KeycloakUserRequestDto convert(final UserRequestDto userRequestDto) {
    return KeycloakUserRequestDto.builder()
        .firstName(userRequestDto.getName())
        .lastName(userRequestDto.getSurname())
        .email(userRequestDto.getEmail())
        .password(userRequestDto.getPassword())
        .build();
  }
}
