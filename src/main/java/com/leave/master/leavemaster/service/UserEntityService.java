package com.leave.master.leavemaster.service;

import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;

import io.vavr.control.Try;

/**
 * Service interface for managing user entities. Defines operations for creating and managing
 * user-related data.
 */
public interface UserEntityService {

  /**
   * Creates a new user based on the provided {@link UserRequestDto}.
   *
   * @param userRequestDto the user data for creating a new user.
   * @return a {@link Try} containing the created {@link UserResponseDto} or an error.
   */
  Try<UserResponseDto> createUser(UserRequestDto userRequestDto);

  Try<UserResponseDto> findUserByEmail(String email);

  Try<UserResponseDto> get(String id);
}
