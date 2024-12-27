package com.leave.master.leavemaster.converter;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/** Resolves and delegates conversion operations to the appropriate {@link Converter}. */
@Component
@RequiredArgsConstructor
public class ConverterResolver {

  private final List<Converter<?, ?>> converters;

  /**
   * Finds the appropriate {@link Converter} for the given source and target types.
   *
   * @param sourceType the source type class.
   * @param targetType the target type class.
   * @param <T> the source type.
   * @param <R> the target type.
   * @return the matching {@link Converter}.
   * @throws IllegalArgumentException if no matching converter is found.
   */
  @SuppressWarnings("unchecked")
  private <T, R> Converter<T, R> converter(Class<?> sourceType, Class<?> targetType) {
    return converters.stream()
        .filter(converter -> converter.support(sourceType, targetType))
        .map(converter -> (Converter<T, R>) converter)
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "No converter found for source type"
                        + sourceType
                        + " and target type "
                        + targetType));
  }

  /**
   * Converts an input object of the given source type to the specified target type.
   *
   * @param sourceType the class of the source type.
   * @param targetType the class of the target type.
   * @param input the input object to be converted.
   * @param <T> the source type.
   * @param <R> the target type.
   * @return a {@link Try} containing the converted object or an error if conversion fails.
   */
  @SuppressWarnings("unchecked")
  public <T, R> Try<R> convert(Class<T> sourceType, Class<R> targetType, T input) {
    return Try.of(() -> (Converter<T, R>) converter(sourceType, targetType))
        .map(converter -> converter.convert(input));
  }
}
