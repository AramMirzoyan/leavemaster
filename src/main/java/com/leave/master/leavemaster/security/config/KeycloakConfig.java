package com.leave.master.leavemaster.security.config;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

  private final LeaveMasterSecurityProperties properties;

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
        .resteasyClient(ResteasyClientBuilder.newBuilder().build())
        .build();
  }

  @Bean
  public TokenService tokenService() {
    return Keycloak.getClientProvider()
        .targetProxy(
            Keycloak.getClientProvider()
                .newRestEasyClient(null, null, false)
                .target(properties.getKeycloak().getUri()),
            TokenService.class);
  }
}
