package com.leave.master.leavemaster.converter;

/**
 * A generic interface for converting objects of one type to another.
 *
 * @param <T> the source type.
 * @param <R> the target type.
 */
public interface Converter<T, R> {
  /**
   * Determines if the conversion between the given source and target types is supported.
   *
   * @param sourceType the type of the source object.
   * @param targetType the type of the target object.
   * @return true if the conversion is supported; false otherwise.
   */
  boolean support(Class<?> sourceType, Class<?> targetType);

  /**
   * Converts an input object of type {@code T} to an output object of type {@code R}.
   *
   * @param input the input object to be converted.
   * @return the converted object of type {@code R}.
   */
  R convert(T input);
}
