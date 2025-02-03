package com.leave.master.leavemaster.service.keycloak;

import java.util.List;
import java.util.Set;

import org.keycloak.representations.idm.UserRepresentation;

import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;

import io.vavr.control.Try;

/**
 * Interface defining Keycloak-related operations for managing users and roles.
 *
 * <p>Provides methods for creating users, retrieving user details, and managing roles.
 */
public interface KeycloakService {

  /**
   * Creates a new user in Keycloak.
   *
   * @param parameter the {@link KeycloakUserRequestDto} containing user details.
   * @return a {@link KeycloakUserResponseDto} representing the created user.
   */
  KeycloakUserResponseDto create(KeycloakUserRequestDto parameter);

  /**
   * Retrieves all users in the Keycloak realm.
   *
   * @return a list of {@link UserRepresentation} representing all users.
   */
  List<UserRepresentation> getAllUser();

  /**
   * Retrieves roles assigned to a user by their username.
   *
   * @param username the username of the user.
   * @return a set of role names associated with the user.
   */
  Set<String> getRolesByUserName(String username);

  /**
   * delete user by user id.
   *
   * @param userId the user id in keycloak
   */
  void deleteUser(String userId);

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
