package com.leave.master.leavemaster.repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.utils.Logging;

import jakarta.ws.rs.core.Response;

/**
 * Repository for managing Keycloak users. Provides methods for creating, retrieving, and checking
 * the existence of users in Keycloak.
 *
 * <p>Designed to interact with the Keycloak Admin API, enabling simplified user management.
 */
public class KeycloakUserRepository {

  private final UsersResource usersResource;

  /**
   * Constructs a new KeycloakUserRepository with the specified {@link UsersResource}.
   *
   * @param usersResource the {@link UsersResource} used for Keycloak user operations.
   */
  public KeycloakUserRepository(final UsersResource usersResource) {
    this.usersResource = usersResource;
  }

  /**
   * Creates a new user in Keycloak.
   *
   * <p>If the user is successfully created, retrieves and returns the user's details.
   *
   * @param userRepresentation the {@link UserRepresentation} of the user to create.
   * @return a {@link KeycloakUserResponseDto} representing the created user.
   * @throws ServiceException if the user creation fails.
   */
  public KeycloakUserResponseDto save(final UserRepresentation userRepresentation) {
    try (var response = usersResource.create(userRepresentation)) {
      if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
        var userId = CreatedResponseUtil.getCreatedId(response);
        Logging.onSuccess(userId, () -> "User created with userId %s".formatted(userId));
        return getById(userId);
      } else {
        throw new ServiceException(
            ServiceErrorCode.UNEXPECTED_ERROR,
            () -> "Failed to create user: " + response.getStatusInfo().toString());
      }
    } catch (Exception e) {
      Logging.onFailed(e, () -> "Error creating user ");
      throw new ServiceException(ServiceErrorCode.BAD_REQUEST);
    }
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param userId the ID of the user to retrieve.
   * @return a {@link KeycloakUserResponseDto} representing the user.
   * @throws ServiceException if the user cannot be found.
   */
  public KeycloakUserResponseDto getById(final String userId) {
    return Optional.ofNullable(usersResource.get(userId))
        .map(UserResource::toRepresentation)
        .map(
            user ->
                KeycloakUserResponseDto.builder()
                    .externalId(userId)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .isEnabled(user.isEnabled())
                    .createdAt(Instant.ofEpochMilli(user.getCreatedTimestamp()))
                    .build())
        .orElseThrow(
            () ->
                new ServiceException(
                    ServiceErrorCode.CAN_NOT_FOUND_DATA,
                    () -> "User not found id: %s".formatted(userId)));
  }

  /**
   * Retrieves the roles of a user grouped by their client ID.
   *
   * <p>This method is private and not intended for external use. It demonstrates how to query user
   * roles in Keycloak.
   *
   * @param userId the ID of the user whose roles to retrieve.
   * @param userResource the {@link UserResource} of the user.
   * @return a {@link Map} of client IDs to role names.
   */
  @SuppressWarnings("java:S1144")
  private Map<String, List<String>> retrivUserRole(
      final String userId, final UserResource userResource) {
    return usersResource
        .get(userId)
        .roles()
        .clientLevel(userResource.toRepresentation().getId())
        .listEffective()
        .stream()
        .collect(
            Collectors.groupingBy(
                RoleRepresentation::getContainerId, // Group by client ID
                Collectors.mapping(RoleRepresentation::getName, Collectors.toList())));
  }

  /**
   * Checks if a user exists in Keycloak by their username.
   *
   * @param username the username to search for.
   * @return {@code true} if the user exists, {@code false} otherwise.
   */
  public boolean isUserExistByUsername(final String username) {
    List<UserRepresentation> search = usersResource.search(username, true);
    return search != null && !search.isEmpty();
  }

  /**
   * delete user by user id.
   *
   * @param userId the user id in keycloak
   */
  public void deleteUser(final String userId) {
    usersResource.delete(userId);
  }
}
