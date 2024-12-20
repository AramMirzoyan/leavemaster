package com.leave.master.leavemaster.converter.userentityconverter;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.model.UserEntity;
import com.leave.master.leavemaster.model.UserRole;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.model.enums.UserStatus;

public class KeycloakResponseDtoConvertToUserEntity
    implements Converter<KeycloakUserResponseDto, UserEntity> {
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == KeycloakUserResponseDto.class && targetType == UserEntity.class;
  }

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
