package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.utils.Logging;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class KeycloakUserRepository {

  private final UsersResource usersResource;

  public KeycloakUserRepository(final UsersResource usersResource) {
    this.usersResource = usersResource;
  }

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

  public   boolean isUserExistByUsername(final String username){
    List<UserRepresentation> search = usersResource.search(username, true);
    return  search!=null && !search.isEmpty();
  }

}
