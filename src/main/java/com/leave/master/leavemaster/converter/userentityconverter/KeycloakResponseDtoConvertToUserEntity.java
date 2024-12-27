package com.leave.master.leavemaster.converter.userentityconverter;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.model.UserEntity;
import com.leave.master.leavemaster.model.UserRole;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.model.enums.UserStatus;

/**
 * Converter implementation for transforming {@link KeycloakUserResponseDto} to {@link UserEntity}.
 *
 * <p>Designed to map Keycloak user response data to the application's user entity format.
 */
public class KeycloakResponseDtoConvertToUserEntity
    implements Converter<KeycloakUserResponseDto, UserEntity> {

  /**
   * Determines whether this converter supports the specified source and target types.
   *
   * @param sourceType the source class type.
   * @param targetType the target class type.
   * @return {@code true} if this converter supports the conversion, {@code false} otherwise.
   */
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == KeycloakUserResponseDto.class && targetType == UserEntity.class;
  }

  /**
   * Converts a {@link KeycloakUserResponseDto} into a {@link UserEntity}.
   *
   * @param source the {@link KeycloakUserResponseDto} to be converted.
   * @return a {@link UserEntity} representing the converted data.
   */
  @Override
  public UserEntity convert(KeycloakUserResponseDto source) {
    return UserEntity.builder()
        .id(source.getExternalId())
        .name(source.getFirstName())
        .surname(source.getLastName())
        .email(source.getEmail())
        .createdAt(LocalDateTime.ofInstant(source.getCreatedAt(), ZoneId.systemDefault()))
        .userStatus(UserStatus.ofEnabledValue(source.isEnabled()))
        .userRole(
            UserRole.builder()
                .name(UserRoleEnum.ofValue(source.getRole().getUserRoleType()))
                .build())
        .build();
  }
}
