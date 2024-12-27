package com.leave.master.leavemaster.model.converter;

import com.leave.master.leavemaster.model.enums.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * Converter for the {@link UserStatus} enum to and from its database representation. Implements the
 * {@link AttributeConverter} interface for JPA entity mapping.
 */
@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

  /**
   * Converts a {@link UserStatus} enum to its corresponding database column representation.
   *
   * @param value the {@link UserStatus} value to convert.
   * @return the string representation of the {@link UserStatus}, or an empty string if {@code
   *     value} is null.
   */
  @Override
  public String convertToDatabaseColumn(UserStatus value) {
    if (value == null) {
      return StringUtils.EMPTY;
    }
    return value.getValue();
  }

  /**
   * Converts a database column value to its corresponding {@link UserStatus} enum.
   *
   * @param value the string value from the database.
   * @return the corresponding {@link UserStatus} enum.
   * @throws IllegalArgumentException if the provided value does not map to a valid {@link
   *     UserStatus}.
   */
  @Override
  public UserStatus convertToEntityAttribute(String value) {
    return UserStatus.ofValue(value);
  }
}
