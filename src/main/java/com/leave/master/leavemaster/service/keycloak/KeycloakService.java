package com.leave.master.leavemaster.service.keycloak;

import java.util.List;
import java.util.Set;

import org.keycloak.representations.idm.UserRepresentation;

import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;

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
}
