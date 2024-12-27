package com.leave.master.leavemaster.security.apiclient;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.security.model.ApiClientRequest;
import com.leave.master.leavemaster.security.model.KcTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API client for interacting with Keycloak's token API.
 *
 * <p>Handles token retrieval and constructs URIs for API interactions.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {
  private final LeaveMasterSecurityProperties properties;
  private final ApiClientRequest apiClientRequest;
  private final RestTemplate restTemplate;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String kcIssueUri;

  /**
   * Retrieves a token response from the Keycloak API.
   *
   * @param source the {@link LoginRequestDto} containing login credentials.
   * @return a {@link KcTokenResponse} containing the token details.
   */
  public KcTokenResponse getTokenResponse(final LoginRequestDto source) {
    URI uri = tokenApiUri();
    var multiValueMapHttpEntity = apiClientRequest.get(source);
    return restTemplate
        .postForEntity(uri, multiValueMapHttpEntity, KcTokenResponse.class)
        .getBody();
  }

  /**
   * Constructs the URI for the token API endpoint.
   *
   * @return the {@link URI} for the token API.
   */
  private URI tokenApiUri() {
    return appBaseUriBuilder()
        .pathSegment(properties.getKeycloak().getTokenEndPoint())
        .build()
        .toUri();
  }

  /**
   * Builds the base URI for Keycloak interactions.
   *
   * @return a {@link UriComponentsBuilder} for constructing Keycloak API URIs.
   */
  private UriComponentsBuilder appBaseUriBuilder() {
    return UriComponentsBuilder.fromHttpUrl(kcIssueUri);
  }
}
