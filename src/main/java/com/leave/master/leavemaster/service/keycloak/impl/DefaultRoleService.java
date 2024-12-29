package com.leave.master.leavemaster.service.keycloak.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.security.Role;
import com.leave.master.leavemaster.service.keycloak.RoleService;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link RoleService} for synchronizing roles with Keycloak.
 *
 * <p>This service handles synchronization of roles between the application and Keycloak, including
 * client and realm roles. Ensure to override methods carefully if subclassing, maintaining the
 * integrity of role synchronization logic.
 */
@Slf4j
@Service
public class DefaultRoleService extends AbstractKeycloakService implements RoleService {

  /**
   * Constructs a new {@code DefaultRoleService} with the provided Keycloak client and security
   * properties.
   *
   * @param keycloak the Keycloak client used for role synchronization
   * @param properties the security properties used for configuring role synchronization
   */
  public DefaultRoleService(
      final Keycloak keycloak, final LeaveMasterSecurityProperties properties) {
    super(keycloak, properties);
  }

  /**
   * Synchronizes roles for the application with Keycloak.
   *
   * <p>If overriding, ensure that roles are prepared and synchronized correctly for both client and
   * realm roles to maintain consistency between the application and Keycloak.
   */
  @Override
  public void syncRoles() {
    syncRoles(Role.findAllByAccess("leavemaster"), getProperties().getKeycloak().getClientId());
  }

  private void syncRoles(final Role[] roles, final String clientId) {
    Optional.ofNullable(prepareRoles(roles))
        .ifPresent(
            newRoles -> {
              syncRolesForClient(newRoles, clientId);
              syncRolesForRealm(newRoles, clientId);
            });
  }

  private void syncRolesForClient(final Set<String> roles, final String clientId) {
    Try.of(() -> roles)
        .andThen(newRoles -> log.info("New roles prepared: {}", newRoles))
        .flatMap(
            newRoles ->
                findClientRoles(clientId).map(prevRoles -> createRoleChanges(newRoles, prevRoles)))
        .flatMap(
            roleChanges ->
                Try.run(
                    () -> {
                      log.info(
                          "Roles to add: {}, Roles to remove: {}",
                          roleChanges.rolesToAdd(),
                          roleChanges.rolesToRemove());
                      manageRoles(
                          clientId, roleChanges.rolesToAdd(), roleChanges.rolesToRemove(), true);
                    }))
        .onFailure(e -> log.error("Failed to sync roles for client: {}", clientId, e));
  }

  private void syncRolesForRealm(final Set<String> roles, final String clientId) {
    Try.of(() -> roles)
        .flatMap(
            newRoles ->
                findRealmRoles().map(existingRoles -> createRoleChanges(newRoles, existingRoles)))
        .flatMap(
            roleChanges ->
                Try.run(
                    () -> {
                      manageRoles(
                          clientId, roleChanges.rolesToAdd(), roleChanges.rolesToRemove, false);
                      roleChanges
                          .rolesToAdd()
                          .forEach(
                              role ->
                                  associateRealmRoleWithClientRole(
                                      role.getRoleName(), clientId, role.getRoleName()));
                    }))
        .onFailure(e -> log.error("Failed to sync roles for realm: {}", e.getMessage()));
  }

  private void associateRealmRoleWithClientRole(
      String realmRoleName, String clientId, String clientRoleName) {
    getRealmResource()
        .onSuccess(
            resource -> {
              ClientRepresentation client = findClient(resource, clientId);
              String clientUuid = client.getId();
              RoleRepresentation clientRole =
                  resource.clients().get(clientUuid).roles().get(clientRoleName).toRepresentation();
              resource.roles().get(realmRoleName).addComposites(List.of(clientRole));
              log.info(
                  "Associated realm role '{}' with client role '{}'",
                  realmRoleName,
                  clientRoleName);
            })
        .onFailure(
            e ->
                log.error(
                    "Failed to associate realm role '{}' with client role '{}': {}",
                    realmRoleName,
                    clientRoleName,
                    e.getMessage()));
  }

  private Set<String> prepareRoles(final Role[] roles) {
    return Stream.of(roles).map(Role::getRoleName).collect(Collectors.toSet());
  }

  private RoleChanges createRoleChanges(Set<String> newRoles, Set<String> prevRoles) {
    Set<String> rolesToRemove =
        prevRoles.stream()
            .filter(
                existingRole ->
                    newRoles.stream().noneMatch(newRole -> newRole.equalsIgnoreCase(existingRole)))
            .collect(Collectors.toSet());

    Set<Role> rolesToAdd =
        newRoles.stream()
            .filter(
                newRole ->
                    prevRoles.stream()
                        .noneMatch(existingRole -> existingRole.equalsIgnoreCase(newRole)))
            .map(Role::findByName)
            .collect(Collectors.toSet());

    return new RoleChanges(rolesToAdd, rolesToRemove);
  }

  private Try<Set<String>> findClientRoles(final String clientId) {
    return clientRoles(clientId)
        .flatMap(
            clientRoles ->
                Try.of(
                    () ->
                        clientRoles
                            .resource()
                            .clients()
                            .get(clientRoles.representation().getId())
                            .roles()
                            .list()
                            .stream()
                            .map(RoleRepresentation::getName)
                            .collect(Collectors.toSet())));
  }

  private void manageRoles(
      String clientId, Set<Role> rolesToAdd, Set<String> rolesToRemove, boolean isClient) {
    if (CollectionUtils.isNotEmpty(rolesToAdd)) {
      if (isClient) {
        addRolesToClient(clientId, rolesToAdd);
      } else {
        addRolesToRealm(rolesToAdd);
      }
    }

    if (CollectionUtils.isNotEmpty(rolesToRemove)) {
      log.info("Roles to remove: {}", rolesToRemove);
      // Implementation for role removal can be added here if needed.
    }
  }

  private void addRolesToClient(String clientId, Set<Role> rolesToAdd) {
    rolesToAdd.forEach(
        role -> {
          RoleRepresentation roleRepresentation = createClientRoleRepresentation(clientId, role);
          getRealmResource()
              .onSuccess(realmResource -> createRole(realmResource, clientId, roleRepresentation))
              .onFailure(e -> log.error("Failed to add role: {}", role.getRoleName(), e));
        });
  }

  private void addRolesToRealm(final Set<Role> rolesToAdd) {
    rolesToAdd.forEach(
        role -> {
          RoleRepresentation realmRoleRepresentation = createRealmRoleRepresentation(role);
          getRealmResource()
              .onSuccess(
                  resource ->  resource.roles().create(realmRoleRepresentation))
              .onFailure(e -> log.error("Failed to add role: {}", role.getRoleName(), e));
        });
  }

  private RoleRepresentation createClientRoleRepresentation(
      final String clientId, final Role role) {
    RoleRepresentation roleRepresentation = new RoleRepresentation();
    roleRepresentation.setName(role.getRoleName());
    roleRepresentation.setDescription(role.getDescription());
    roleRepresentation.setClientRole(true);
    roleRepresentation.setComposite(false);
    roleRepresentation.setContainerId(clientId);
    return roleRepresentation;
  }

  private RoleRepresentation createRealmRoleRepresentation(final Role role) {
    RoleRepresentation realmRole = new RoleRepresentation();
    realmRole.setName(role.getRoleName());
    realmRole.setDescription(role.getRealmDescription());
    realmRole.setComposite(true);

    return realmRole;
  }

  private void createRole(
      RealmResource realmResource, String clientId, RoleRepresentation roleRepresentation) {
    Try.run(
            () -> {
              var client =
                  realmResource.clients().findByClientId(clientId).stream()
                      .findFirst()
                      .orElseThrow(
                          () ->
                              new IllegalStateException(
                                  "Client with clientId '" + clientId + "' not found."));
              var clientResource = realmResource.clients().get(client.getId());
              log.info(
                  "Creating role '{}' for client '{}'.", roleRepresentation.getName(), clientId);
              clientResource.roles().create(roleRepresentation);
            })
        .onFailure(
            e ->
                log.error(
                    "Failed to create role '{}': {}",
                    roleRepresentation.getName(),
                    e.getMessage()));
  }

  private Try<ClientRoles> clientRoles(final String clientId) {
    return getRealmResource()
        .map(realmResource -> new ClientRoles(realmResource, findClient(realmResource, clientId)));
  }

  private Try<Set<String>> findRealmRoles() {
    return getRealmResource()
        .map(RealmResource::roles)
        .map(RolesResource::list)
        .map(
            roleRepresentations ->
                roleRepresentations.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toSet()));
  }

  private ClientRepresentation findClient(final RealmResource resource, final String clientId) {
    return resource.clients().findByClientId(clientId).stream()
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Client not found: " + clientId));
  }

  private record ClientRoles(RealmResource resource, ClientRepresentation representation) {}

  private record RoleChanges(Set<Role> rolesToAdd, Set<String> rolesToRemove) {}
}
