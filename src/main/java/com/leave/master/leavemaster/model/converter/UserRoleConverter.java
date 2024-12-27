package com.leave.master.leavemaster.model.converter;

import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.utils.Utils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRoleEnum, String> {
  @Override
  public String convertToDatabaseColumn(final UserRoleEnum value) {
    Utils.isNull(value, () -> "UserRole value is null");
    return value.getName();
  }

  @Override
  public UserRoleEnum convertToEntityAttribute(final String value) {
    return UserRoleEnum.ofValue(value);
  }
}
