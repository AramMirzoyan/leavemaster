package com.leave.master.leavemaster.converter.roleconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.security.Role;

/**
 * Converter implementation for transforming {@link UserRoleEnum} to {@link Role}.
 *
 * <p>Designed to map application-specific user roles to security roles.
 */
public class UserRoleToSecurityConverter implements Converter<UserRoleEnum, Role> {

  /**
   * Determines whether this converter supports the specified source and target types.
   *
   * @param sourceType the source class type.
   * @param targetType the target class type.
   * @return {@code true} if this converter supports the conversion, {@code false} otherwise.
   */
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == UserRoleEnum.class && targetType == Role.class;
  }

  /**
   * Converts a {@link UserRoleEnum} into a {@link Role}.
   *
   * @param source the {@link UserRoleEnum} to be converted.
   * @return a {@link Role} representing the converted data.
   */
  @Override
  public Role convert(final UserRoleEnum source) {
    return Role.findByUserRoleType(source.getName());
  }
}
