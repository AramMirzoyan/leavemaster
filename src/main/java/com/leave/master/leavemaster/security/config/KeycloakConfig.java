package com.leave.master.leavemaster.security.config;

import java.util.Objects;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;

import jakarta.ws.rs.client.ClientBuilder;
import lombok.RequiredArgsConstructor;

/**
 * Configuration class for Keycloak integration.
 *
 * <p>Provides beans for configuring the Keycloak client and token service. Designed for extension
 * if additional Keycloak-related configurations are required.
 */
@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

  private final LeaveMasterSecurityProperties properties;

  /**
   * Configures the Keycloak client bean for interacting with the Keycloak Admin API.
   *
   * <p>Subclasses can override this method to customize the Keycloak client configuration.
   *
   * @return a configured {@link Keycloak} instance.
   * @throws NullPointerException if the Keycloak properties are not set.
   */
  @Bean
  public Keycloak keycloak() {
    var prop = Objects.requireNonNull(properties.getKeycloak());

    return KeycloakBuilder.builder()
        .serverUrl(prop.getUri())
        .grantType(OAuth2Constants.PASSWORD)
        .realm(prop.getAdmin().getRealm())
        .clientId(prop.getAdmin().getClientId())
        .clientSecret(prop.getAdmin().getClientSecret())
        .username(prop.getAdmin().getUserName())
        .password(prop.getAdmin().getPassword())
        .resteasyClient(ClientBuilder.newBuilder().build())
        .build();
  }

  /**
   * Configures the TokenService bean for handling token-related operations in Keycloak.
   *
   * <p>Subclasses can override this method to customize the token service configuration.
   *
   * @return a {@link TokenService} instance for token operations.
   */
  @Bean
  @SuppressWarnings("all")
  public TokenService tokenService() {
    return Keycloak.getClientProvider()
        .targetProxy(
            Keycloak.getClientProvider()
                .newRestEasyClient(null, null, false)
                .target(properties.getKeycloak().getUri()),
            TokenService.class);
  }
}
