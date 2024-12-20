package com.leave.master.leavemaster.converter.roleconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.security.Role;

public class UserRoleToSecurityConverter implements Converter<UserRoleEnum, Role> {
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == UserRoleEnum.class && targetType == Role.class;
  }

  @Override
  public Role convert(final UserRoleEnum source) {
    return Role.findByUserRoleType(source.getName());
  }
}
