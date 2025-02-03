package com.leave.master.leavemaster.service;

import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;

import io.vavr.control.Try;

/**
 * Service interface for managing user entities. Defines operations for creating, retrieving, and
 * managing user-related data.
 */
public interface UserEntityService {

  /**
   * Creates a new user based on the provided {@link UserRequestDto}.
   *
   * @param userRequestDto the user data for creating a new user.
   * @return a {@link Try} containing the created {@link UserResponseDto} or an error.
   */
  Try<UserResponseDto> createUser(UserRequestDto userRequestDto);

  /**
   * Finds a user by their email address.
   *
   * @param email the email address of the user to find.
   * @return a {@link Try} containing the found {@link UserResponseDto} or an error if the user is
   *     not found.
   */
  Try<UserResponseDto> findUserByEmail(String email);

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id the unique identifier of the user.
   * @return a {@link Try} containing the {@link UserResponseDto} or an error if the user is not
   *     found.
   */
  Try<UserResponseDto> findById(String id);

  /**
   * Changes the password for a user in Keycloak.
   *
   * <p>This method updates the password of the user with the given user ID.
   *
   * @param userId The ID of the user whose password will be changed.
   * @param password The new password to be set.
   * @return A {@link Try} representing success or failure.
   */
  @SuppressWarnings("all")
  Try<Void> changePassword(String userId, String password);
}
