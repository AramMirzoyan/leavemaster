package com.leave.master.leavemaster.converter.userentityconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.model.UserEntity;

/** Converts a {@link UserEntity} to a {@link UserResponseDto}. */
public class EntityToUserDtoConverter implements Converter<UserEntity, UserResponseDto> {

  /**
   * Determines if this converter supports the given source and target types.
   *
   * @param sourceType the type of the source object.
   * @param targetType the type of the target object.
   * @return true if the source type is {@link UserEntity} and the target type is {@link
   *     UserResponseDto}.
   */
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == UserEntity.class && targetType == UserResponseDto.class;
  }

  /**
   * Converts a {@link UserEntity} into a {@link UserResponseDto}.
   *
   * @param input the {@link UserEntity} to convert.
   * @return the converted {@link UserResponseDto}.
   */
  @Override
  public UserResponseDto convert(final UserEntity input) {
    return UserResponseDto.builder()
        .id(input.getId())
        .name(input.getName())
        .surname(input.getSurname())
        .email(input.getEmail())
        .role(input.getUserRole().getName())
        .createdAt(input.getCreatedAt())
        .status(input.getUserStatus())
        .build();
  }
}
