package com.leave.master.leavemaster.converter.roleconverter;

import com.leave.master.leavemaster.converter.Converter;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.security.Role;

public class SecurityRoleToUserRoleConverter implements Converter<Role, UserRoleEnum> {
  @Override
  public boolean support(Class<?> sourceType, Class<?> targetType) {
    return sourceType == Role.class && targetType == UserRoleEnum.class;
  }

  @Override
  public UserRoleEnum convert(final Role source) {
    return UserRoleEnum.ofValue(source.getUserRoleType());
  }
}
