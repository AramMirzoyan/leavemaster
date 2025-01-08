package com.leave.master.leavemaster;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.apache.http.client.utils.URIBuilder;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.leave.master.leavemaster.containers.KeycloakTestContainer;
import com.leave.master.leavemaster.containers.MysqlContainer;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
@SpringBootTest
@Testcontainers
@DirtiesContext
class LeavemasterApplicationTests {

    @Container
    public static MysqlContainer mysqlContainer = MysqlContainer.getInstance();

    @Container
     public static KeycloakContainer keycloak = KeycloakTestContainer.getInstance();

  @Test
  void contextLoads() throws URISyntaxException {
//    keycloak.start();

      URI authorizationURI = new URIBuilder(
              keycloak.getAuthServerUrl() + "/realms/leavemaster-realm/protocol/openid-connect/token")
              .build();

      WebClient webClient = WebClient.builder().build();

      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.put("grant_type", java.util.Collections.singletonList("password"));
      formData.put("client_id", java.util.Collections.singletonList("leavemaster_client"));
      formData.put("username", java.util.Collections.singletonList("admin"));
      formData.put("password", java.util.Collections.singletonList("admin"));
      formData.put("client_secret", java.util.Collections.singletonList("1pYXuSxqnix14PJvBbCERWj3uXFfGSdI"));

      String result = webClient
              .post()
              .uri(authorizationURI)
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .body(BodyInserters.fromFormData(formData))
              .retrieve()
              .bodyToMono(String.class)
              .block();

      JacksonJsonParser jsonParser = new JacksonJsonParser();
      String token = "Bearer " + jsonParser.parseMap(result).get("access_token").toString();
  }
}
