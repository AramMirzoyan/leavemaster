package com.leave.master.leavemaster.service.keycloak.impl;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.repository.KeycloakUserRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.lang.NonNull;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractKeycloakService {

  protected final Keycloak keycloak;
  protected final LeaveMasterSecurityProperties properties;

  @NonNull protected Try<RealmResource> getRealmResource() {
    return Try.of(() -> keycloak.realm(properties.getKeycloak().getRealm()));
  }

  protected boolean existUserWithEmail(
      @NonNull final UsersResource userResource, final String email) {
    return userResource.searchByEmail(email, true).stream().findFirst().isPresent();
  }

  @NonNull protected KeycloakUserRepository userRepository(@NonNull final UsersResource userResource) {
    return new KeycloakUserRepository(userResource);
  }

  protected String clientId() {
    return properties.getKeycloak().getClientId();
  }

  protected String realm() {
    return properties.getKeycloak().getRealm();
  }

  protected List<ClientRepresentation> clients() {
    return keycloak.realm(realm()).clients().findByClientId(clientId());
  }
}
