package com.leave.master.leavemaster.security.model;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;

/**
 * Functional interface for constructing HTTP requests for API clients.
 *
 * <p>This interface provides a method to create an {@link HttpEntity} with a {@link MultiValueMap}
 * payload from a given {@link LoginRequestDto}.
 */
@FunctionalInterface
public interface ApiClientRequest {

  /**
   * Constructs an {@link HttpEntity} containing a {@link MultiValueMap} of request parameters based
   * on the provided {@link LoginRequestDto}.
   *
   * @param dto the {@link LoginRequestDto} containing the data for the request.
   * @return an {@link HttpEntity} with the constructed request parameters.
   */
  HttpEntity<MultiValueMap<String, String>> get(LoginRequestDto dto);
}
