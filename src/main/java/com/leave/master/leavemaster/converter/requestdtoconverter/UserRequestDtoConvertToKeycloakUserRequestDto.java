package com.leave.master.leavemaster.converter.requestdtoconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;

/**
 * Converter implementation for transforming {@link UserRequestDto} to {@link
 * KeycloakUserRequestDto}.
 *
 * <p>Designed to support the conversion process between user request DTOs and Keycloak-specific
 * DTOs.
 */
public class UserRequestDtoConvertToKeycloakUserRequestDto
    implements Converter<UserRequestDto, KeycloakUserRequestDto> {

  /**
   * Determines whether this converter supports the specified source and target types.
   *
   * @param sourceType the source class type.
   * @param targetType the target class type.
   * @return {@code true} if this converter supports the conversion, {@code false} otherwise.
   */
  @Override
  public boolean support(final Class<?> sourceType, final Class<?> targetType) {
    return sourceType == UserRequestDto.class && targetType == KeycloakUserRequestDto.class;
  }

  /**
   * Converts a {@link UserRequestDto} into a {@link KeycloakUserRequestDto}.
   *
   * @param userRequestDto the {@link UserRequestDto} to be converted.
   * @return a {@link KeycloakUserRequestDto} containing the converted data.
   */
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
