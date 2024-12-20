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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultKeycloakService extends AbstractKeycloakService implements KeycloakService {
  public DefaultKeycloakService(
      final Keycloak keycloak, final LeaveMasterSecurityProperties properties) {
    super(keycloak, properties);
  }

  @Override
  public synchronized KeycloakUserResponseDto create(final KeycloakUserRequestDto parameter) {
    log.info("start to create user in keycloak");
    return getRealmResource()
        .map(RealmResource::users)
        .andThen(
            usersResource -> {
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

  @Override
  public List<UserRepresentation> getAllUser() {
    return keycloak.realm(realm()).users().list();
  }

  @Override
  public Set<String> getRolesByUserName(String username) {
    RealmResource realmResource = keycloak.realm(properties.getKeycloak().getRealm());
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

  protected CredentialRepresentation passwordCredential(final String password) {
    log.info("start to assigned password for user");
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setTemporary(false);
    credential.setValue(password);
    log.info("finish to assigned password for user");
    return credential;
  }

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
    keycloak
        .realm(realm())
        .users()
        .get(userResponseDto.getExternalId())
        .roles()
        .clientLevel(clientRepId)
        .add(
            Collections.singletonList(
                creatRoleRepresentation(clientRepId, parameter.getRole().getRoleName())));
  }

  private RoleRepresentation retrivRoles(final KeycloakUserResponseDto userResponseDto) {
    return keycloak
        .realm(realm())
        .users()
        .get(userResponseDto.getExternalId())
        .roles()
        .clientLevel(clients().getFirst().getId())
        .listAll()
        .getFirst();
  }

  protected RoleRepresentation creatRoleRepresentation(
      final String clientRepId, final String roleName) {
    return keycloak
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
