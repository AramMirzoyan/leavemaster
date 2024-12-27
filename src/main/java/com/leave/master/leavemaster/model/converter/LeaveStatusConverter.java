package com.leave.master.leavemaster.model.converter;

import com.leave.master.leavemaster.model.enums.LeaveStatus;
import com.leave.master.leavemaster.utils.Utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter for the {@link LeaveStatus} enum to and from its database representation. Implements
 * the {@link AttributeConverter} interface for JPA entity mapping.
 */
@Converter
public class LeaveStatusConverter implements AttributeConverter<LeaveStatus, String> {

  /**
   * Converts a {@link LeaveStatus} enum to its corresponding database column representation.
   *
   * @param value the {@link LeaveStatus} value to convert.
   * @return the string representation of the {@link LeaveStatus}, or an empty string if {@code
   *     value} is null.
   */
  @Override
  public String convertToDatabaseColumn(LeaveStatus value) {
    Utils.isNull(value, () -> "The LeaveStatus value is null");
    return value.getValue();
  }

  /**
   * Converts a database column value to its corresponding {@link LeaveStatus} enum.
   *
   * @param value the string value from the database.
   * @return the corresponding {@link LeaveStatus} enum.
   * @throws IllegalArgumentException if the provided value does not map to a valid {@link
   *     LeaveStatus}.
   */
  @Override
  public LeaveStatus convertToEntityAttribute(String value) {
    return LeaveStatus.ofValue(value);
  }
}
