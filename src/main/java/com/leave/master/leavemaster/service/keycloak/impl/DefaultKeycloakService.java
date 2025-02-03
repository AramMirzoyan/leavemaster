package com.leave.master.leavemaster.service.keycloak.impl;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.repository.KeycloakUserRepository;
import com.leave.master.leavemaster.security.Role;
import com.leave.master.leavemaster.service.keycloak.KeycloakService;
import com.leave.master.leavemaster.service.keycloak.model.UserRepresentationBuilder;
import com.leave.master.leavemaster.utils.Logging;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the {@link KeycloakService} interface.
 *
 * <p>Provides methods for creating users, retrieving roles, and interacting with Keycloak's Admin
 * API.
 */
@Service
@Slf4j
public class DefaultKeycloakService extends AbstractKeycloakService implements KeycloakService {

  /**
   * Constructs a new {@code DefaultKeycloakService} instance.
   *
   * @param keycloak the {@link Keycloak} client for accessing Keycloak Admin API.
   * @param properties the security properties for the LeaveMaster application.
   */
  public DefaultKeycloakService(
      final Keycloak keycloak, final LeaveMasterSecurityProperties properties) {
    super(keycloak, properties);
  }

  /**
   * Creates a new user in Keycloak.
   *
   * @param parameter the {@link KeycloakUserRequestDto} containing user details.
   * @return the created {@link KeycloakUserResponseDto}.
   */
  @Override
  public synchronized KeycloakUserResponseDto create(final KeycloakUserRequestDto parameter) {
    log.info("start to create user in keycloak");
    return getRealmResource()
        .map(RealmResource::users)
        .andThen(
            usersResource -> {
              if (userRepository(usersResource).isUserExistByUsername(parameter.getEmail())) {
                throw new ServiceException(
                    ServiceErrorCode.BAD_REQUEST,
                    () -> "User with username '%s' already exists".formatted(parameter.getEmail()));
              }

              if (existUserWithEmail(usersResource, parameter.getEmail())) {
                throw new ServiceException(
                    ServiceErrorCode.BAD_REQUEST,
                    () -> "User with email '%s' already exists".formatted(parameter.getEmail()));
              }
            })
        .map(
            usersResource -> new KeycloakUserResource(userRepository(usersResource), usersResource))
        .map(
            keycloakUserResource ->
                Pair.of(keycloakUserResource, userRepresentationBuilder(parameter)))
        .map(
            userRepresentation ->
                Pair.of(
                    userRepresentation.getLeft(),
                    userRepresentation.getLeft().repository().save(userRepresentation.getRight())))
        .andThen(it -> assignedRole(it.getValue(), parameter))
        .map(
            it ->
                it.getValue().toBuilder()
                    .role(Role.findByName(retrivRoles(it.getValue()).getName()))
                    .build())
        .onSuccess(
            userResponseDto ->
                Logging.onSuccess(
                    () ->
                        "Finish to create user in keycloak userId: %s"
                            .formatted(userResponseDto.getExternalId())))
        .onFailure(
            th ->
                Logging.onFailed(
                    () ->
                        "Failed to create user in keycloak: message: %s"
                            .formatted(th.getMessage())))
        .get();
  }

  /**
   * Retrieves all users from the Keycloak realm.
   *
   * @return a list of {@link UserRepresentation}.
   */
  @Override
  public List<UserRepresentation> getAllUser() {
    return getKeycloak().realm(realm()).users().list();
  }

  /**
   * Retrieves roles assigned to a user by their username.
   *
   * @param username the username of the user.
   * @return a set of role names.
   */
  @Override
  public Set<String> getRolesByUserName(String username) {
    RealmResource realmResource = getKeycloak().realm(getProperties().getKeycloak().getRealm());
    UsersResource users = realmResource.users();
    UserRepresentation userRepresentation =
        users.search(username).stream()
            .findFirst()
            .orElseThrow(() -> new ServiceException(ServiceErrorCode.CAN_NOT_FOUND_DATA));

    var clientMappings = users.get(userRepresentation.getId()).roles().getAll().getClientMappings();
    Set<String> roles = new HashSet<>();

    if (clientMappings != null) {
      roles.addAll(
          clientMappings.get(clientId()).getMappings().stream()
              .map(RoleRepresentation::getName)
              .toList());
    }
    return roles;
  }

  /**
   * Deletes a user from Keycloak.
   *
   * <p>Subclasses may override this method to add additional logic before or after user deletion.
   *
   * @param userId The ID of the user to be deleted.
   */
  @Override
  public void deleteUser(String userId) {
    getRealmResource()
        .map(RealmResource::users)
        .map(this::userRepository)
        .onSuccess(repository -> repository.deleteUser(userId));
  }

  /**
   * Changes the password of a user in Keycloak.
   *
   * <p>Subclasses may override this method to enforce additional security checks.
   *
   * @param userId The ID of the user whose password will be changed.
   * @param password The new password.
   * @return A Try<Void> representing success or failure.
   */
  @Override
  public Try<Void> changePassword(final String userId, final String password) {
    log.debug("start to changed password");
    return changedUserPassword(userId, password);
  }

  /**
   * Creates a {@link CredentialRepresentation} for a user's password.
   *
   * @param password the password to assign.
   * @return a {@link CredentialRepresentation}.
   */
  protected CredentialRepresentation passwordCredential(final String password) {
    log.info("start to assigned password for user");
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setTemporary(false);
    credential.setValue(password);
    log.info("finish to assigned password for user");
    return credential;
  }

  /**
   * Updates the password of a user in Keycloak.
   *
   * <p>Designed to be overridden by subclasses if necessary. Uses a {@link
   * CredentialRepresentation} for setting the new password.
   *
   * @param userId The ID of the user whose password is being changed.
   * @param password The new password.
   * @return A Try<Void> representing success or failure.
   */
  protected Try<Void> changedUserPassword(final String userId, final String password) {
    CredentialRepresentation credentialRepresentation = passwordCredential(password);

    return Try.run(
        () ->
            getRealmResource()
                .map(RealmResource::users)
                .andThen(users -> users.get(userId).resetPassword(credentialRepresentation))
                .onFailure(this::recoverFromUnexpectedError));
  }

  @SuppressWarnings("all")
  private Try<Void> recoverFromUnexpectedError(final Throwable th) {
    return Try.failure(new ServiceException(ServiceErrorCode.UNEXPECTED_ERROR, th::getMessage));
  }

  /**
   * Builds a {@link UserRepresentation} for creating a new user.
   *
   * @param parameter the user details.
   * @return a {@link UserRepresentation}.
   */
  protected UserRepresentation userRepresentationBuilder(final KeycloakUserRequestDto parameter) {
    return UserRepresentationBuilder.builder()
        .enabled(true)
        .emailVerified(true)
        .username(parameter.getEmail())
        .firstName(parameter.getFirstName())
        .lastName(parameter.getLastName())
        .email(parameter.getEmail())
        .credentials(Collections.singletonList(passwordCredential(parameter.getPassword())))
        .clientRoles(Map.of(clientId(), List.of(parameter.getRole().getRoleName())))
        .build();
  }

  private void assignedRole(
      final KeycloakUserResponseDto userResponseDto, final KeycloakUserRequestDto parameter) {
    var clientRepId = clients().getFirst().getId();
    getKeycloak()
        .realm(realm())
        .users()
        .get(userResponseDto.getExternalId())
        .roles()
        .clientLevel(clientRepId)
        .add(
            Collections.singletonList(
                creatRoleRepresentation(clientRepId, parameter.getRole().getRoleName())));
    assignRealmRole(userResponseDto.getExternalId(), parameter.getRole().getRoleName());
  }

  private void assignRealmRole(String userId, String realmRoleName) {
    getKeycloak()
        .realm(realm())
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .add(
            Collections.singletonList(
                getKeycloak().realm(realm()).roles().get(realmRoleName).toRepresentation()));
  }

  private RoleRepresentation retrivRoles(final KeycloakUserResponseDto userResponseDto) {
    return getKeycloak()
        .realm(realm())
        .users()
        .get(userResponseDto.getExternalId())
        .roles()
        .clientLevel(clients().getFirst().getId())
        .listAll()
        .getFirst();
  }

  /**
   * Creates a {@link RoleRepresentation} for a client role in Keycloak.
   *
   * @param clientRepId the client representation ID.
   * @param roleName the role name.
   * @return a {@link RoleRepresentation}.
   */
  protected RoleRepresentation creatRoleRepresentation(
      final String clientRepId, final String roleName) {
    return getKeycloak()
        .realm(realm())
        .clients()
        .get(clientRepId)
        .roles()
        .get(roleName)
        .toRepresentation();
  }

  protected record KeycloakUserResource(
      KeycloakUserRepository repository, UsersResource userResource) {}
}
