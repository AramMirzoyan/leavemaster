package com.leave.master.leavemaster.model.converter;

import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.utils.Utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class for mapping {@link UserRoleEnum} to its database representation and vice versa.
 * This class implements {@link AttributeConverter} to provide custom conversion logic.
 *
 * <p>If subclassing, ensure to maintain consistent conversion logic to avoid database
 * inconsistency.
 */
@Converter
public class UserRoleConverter implements AttributeConverter<UserRoleEnum, String> {

  /**
   * Converts a {@link UserRoleEnum} to its database column representation. Subclasses overriding
   * this method should ensure null checks are performed and maintain consistency with the database
   * schema.
   *
   * @param value the user role enumeration to convert
   * @return the string representation of the user role
   */
  @Override
  public final String convertToDatabaseColumn(final UserRoleEnum value) {
    Utils.isNull(value, () -> "UserRole value is null");
    return value.getName();
  }

  /**
   * Converts a database column value (String) to its corresponding {@link UserRoleEnum}
   * representation.
   *
   * <p>Subclasses overriding this method should ensure the string value provided matches one of the
   * defined enum values in {@link UserRoleEnum}, and handle cases where the value is invalid.
   *
   * @param value the string representation of a user role from the database
   * @return the corresponding {@link UserRoleEnum}, never null
   * @throws IllegalArgumentException if the provided value does not map to any enum value
   */
  @Override
  public final UserRoleEnum convertToEntityAttribute(final String value) {
    return UserRoleEnum.ofValue(value);
  }
}
