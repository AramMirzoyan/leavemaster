package com.leave.master.leavemaster.containers;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

import static com.leave.master.leavemaster.containers.Constant.*;
import static java.util.Collections.singletonList;

@Testcontainers
@DirtiesContext
public class LeaveMasterAuthToken {

    @Container
    public static KeycloakContainer keycloak = KeycloakTestContainer.getInstance();

    private Function<KeycloakContainer, URIBuilder> authorizationURI= keycloakContainer ->
    {
        try {
            return new URIBuilder(keycloakContainer.getAuthServerUrl().concat("/realms/leavemaster-realm/protocol/openid-connect/token"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    };


    public String authToken(){
   return  null;
    }


    private MultiValueMap<String, String> mappingFormData(final String username,final String password){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("grant_type", singletonList(PASSWORD.getValue()));
        formData.put("client_id", singletonList(CLIENT_ID.getValue()));
        formData.put("username", singletonList("admin"));
        formData.put("password", singletonList("admin"));
        formData.put("client_secret", singletonList(CLIENT_SECRET.getValue()));

        return formData;
    }









}
