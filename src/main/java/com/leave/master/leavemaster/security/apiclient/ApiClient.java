package com.leave.master.leavemaster.security.apiclient;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.security.model.ApiClientRequest;
import com.leave.master.leavemaster.security.model.KcTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiClient {
  private final LeaveMasterSecurityProperties properties;
  private final ApiClientRequest apiClientRequest;
  private final RestTemplate restTemplate;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String kcIssueUri;

  public KcTokenResponse getTokenResponse(final LoginRequestDto source) {
    URI uri = tokenApiUri();
    var  multiValueMapHttpEntity = apiClientRequest.get(source);
//
//    restTemplate.exchange(
//            uri,
//            HttpMethod.POST,
//          multiValueMapHttpEntity,
//          new ParameterizedTypeReference<KcTokenResponse>(){}
//    );

      return restTemplate
              .postForEntity(uri, multiValueMapHttpEntity, KcTokenResponse.class).getBody();


  }

  private URI tokenApiUri() {
    return appBaseUriBuilder()
        .pathSegment(properties.getKeycloak().getTokenEndPoint())
        .build()
        .toUri();
  }

  private UriComponentsBuilder appBaseUriBuilder() {
    return UriComponentsBuilder.fromHttpUrl(kcIssueUri);
  }
}
