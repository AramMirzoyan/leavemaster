package com.leave.master.leavemaster.converter.roleconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.security.Role;

/**
 * Converter implementation for transforming {@link Role} to {@link UserRoleEnum}.
 *
 * <p>Designed to map security roles to user roles in the application.
 */
public class SecurityRoleToUserRoleConverter implements Converter<Role, UserRoleEnum> {

  /**
   * Determines whether this converter supports the specified source and target types.
   *
   * @param sourceType the source class type.
   * @param targetType the target class type.
   * @return {@code true} if this converter supports the conversion, {@code false} otherwise.
   */
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == Role.class && targetType == UserRoleEnum.class;
  }

  /**
   * Converts a {@link Role} into a {@link UserRoleEnum}.
   *
   * @param source the {@link Role} to be converted.
   * @return a {@link UserRoleEnum} representing the converted data.
   */
  @Override
  public UserRoleEnum convert(final Role source) {
    return UserRoleEnum.ofValue(source.getUserRoleType());
  }
}
