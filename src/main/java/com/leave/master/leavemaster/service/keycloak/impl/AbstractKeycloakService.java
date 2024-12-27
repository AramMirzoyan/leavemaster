package com.leave.master.leavemaster.service.keycloak.impl;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.lang.NonNull;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.repository.KeycloakUserRepository;
import com.leave.master.leavemaster.utils.Logging;

import io.vavr.control.Try;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Abstract service providing common functionality for interacting with Keycloak. This class is
 * designed for extension by specific Keycloak service implementations.
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractKeycloakService {
  /** The Keycloak client instance used for administrative operations. */
  private final Keycloak keycloak;

  /** The security properties configuration for LeaveMaster. */
  private final LeaveMasterSecurityProperties properties;

  /**
   * Retrieves the realm resource for the current Keycloak configuration.
   *
   * @return a {@link Try} wrapping the {@link RealmResource}.
   */
  protected Try<RealmResource> getRealmResource() {
    return Try.of(() -> keycloak.realm(properties.getKeycloak().getRealm()))
        .onFailure(th -> Logging.onFailed(th::getMessage));
  }

  /**
   * Checks if a user with the given email exists.
   *
   * @param userResource the {@link UsersResource} to search within.
   * @param email the email to search for.
   * @return {@code true} if a user with the email exists, {@code false} otherwise.
   */
  protected boolean existUserWithEmail(
      @NonNull final UsersResource userResource, final String email) {
    return userResource.searchByEmail(email, true).stream().findFirst().isPresent();
  }

  /**
   * Creates a repository for Keycloak users.
   *
   * @param userResource the {@link UsersResource} instance.
   * @return a {@link KeycloakUserRepository} instance.
   */
  protected KeycloakUserRepository userRepository(@NonNull final UsersResource userResource) {
    return new KeycloakUserRepository(userResource);
  }

  /**
   * Retrieves the client ID from the configuration.
   *
   * @return the client ID.
   */
  protected String clientId() {
    return properties.getKeycloak().getClientId();
  }

  /**
   * Retrieves the realm name from the configuration.
   *
   * @return the realm name.
   */
  protected String realm() {
    return properties.getKeycloak().getRealm();
  }

  /**
   * Retrieves the list of clients for the current realm and client ID.
   *
   * @return a list of {@link ClientRepresentation}.
   */
  protected List<ClientRepresentation> clients() {
    return keycloak.realm(realm()).clients().findByClientId(clientId());
  }
}
