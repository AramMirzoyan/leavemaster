package com.leave.master.leavemaster.containers;

import static com.leave.master.leavemaster.containers.Constant.*;
import static java.util.Collections.singletonList;

import java.net.URISyntaxException;
import java.util.function.Function;

import org.apache.http.client.utils.URIBuilder;
import org.keycloak.OAuth2Constants;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import dasniko.testcontainers.keycloak.KeycloakContainer;

/**
 * Provides methods for interacting with the Keycloak authorization server.
 *
 * <p>This class is responsible for managing the Keycloak container and retrieving authentication
 * tokens for user credentials. It uses the Testcontainers library to manage the Keycloak container
 * and Spring's WebClient for HTTP requests.
 */
@Testcontainers
@DirtiesContext
public final class LeaveMasterAuthToken {

  /** The Keycloak container instance used for testing authentication. */
  @Container private static final KeycloakContainer KEYCLOAK = KeycloakTestContainer.getInstance();

  private final WebClient webClient;

  /**
   * Constructs a new instance of {@code LeaveMasterAuthToken}. Starts the Keycloak container if it
   * is not already running.
   */
  public LeaveMasterAuthToken() {
    this.webClient = WebClient.builder().build();
    if (!KEYCLOAK.isRunning()) {
      KEYCLOAK.start();
    }
  }

  /** A function that generates the authorization URI for the Keycloak server. */
  private final Function<KeycloakContainer, URIBuilder> authorizationURI =
      keycloakContainer -> {
        try {
          return new URIBuilder(
              keycloakContainer
                  .getAuthServerUrl()
                  .concat("/realms/leavemaster-realm/protocol/openid-connect/token"));
        } catch (URISyntaxException e) {
          throw new RuntimeException(e);
        }
      };

  /**
   * Retrieves an authentication token for the specified user credentials.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return the access token as a string
   * @throws URISyntaxException if the URI for the Keycloak server is invalid
   */
  public String authToken(final String username, final String password) throws URISyntaxException {
    if (!KEYCLOAK.isRunning()) {
      throw new IllegalStateException("Keycloak container is not running");
    }

    String result =
        webClient
            .post()
            .uri(authorizationURI.apply(KEYCLOAK).build())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(mappingFormData(username, password)))
            .retrieve()
            .bodyToMono(String.class)
            .block();

    return new JacksonJsonParser().parseMap(result).get("access_token").toString();
  }

  /**
   * Maps the form data for the Keycloak authentication request.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return a {@link MultiValueMap} containing the form data
   */
  private MultiValueMap<String, String> mappingFormData(
      final String username, final String password) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.put(OAuth2Constants.GRANT_TYPE, singletonList(PASSWORD.getValue()));
    formData.put(OAuth2Constants.CLIENT_ID, singletonList(CLIENT_ID.getValue()));
    formData.put("username", singletonList(username));
    formData.put("password", singletonList(password));
    formData.put(OAuth2Constants.CLIENT_SECRET, singletonList(CLIENT_SECRET.getValue()));

    return formData;
  }
}
