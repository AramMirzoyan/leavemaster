package com.leave.master.leavemaster.converter.userentityconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.model.UserEntity;

/** Converts a {@link UserRequestDto} to a {@link UserEntity}. */
public class UserDtoToEntityConverter implements Converter<UserRequestDto, UserEntity> {
  /**
   * Determines if this converter supports the given source and target types.
   *
   * @param sourceType the type of the source object.
   * @param targetType the type of the target object.
   * @return true if the source type is {@link UserRequestDto} and the target type is {@link
   *     UserEntity}.
   */
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == UserRequestDto.class && targetType == UserEntity.class;
  }

  /**
   * Converts a {@link UserRequestDto} into a {@link UserEntity}.
   *
   * @param input the {@link UserRequestDto} to convert.
   * @return the converted {@link UserEntity}.
   */
  @Override
  public UserEntity convert(final UserRequestDto input) {
    return UserEntity.builder()
        .name(input.getName())
        .surname(input.getSurname())
        .email(input.getEmail())
        .build();
  }
}
