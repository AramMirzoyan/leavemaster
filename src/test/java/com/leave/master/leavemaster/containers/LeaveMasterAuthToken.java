package com.leave.master.leavemaster.containers;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;

@Testcontainers
@DirtiesContext
public class LeaveMasterAuthToken {

    @Container
    public static KeycloakContainer keycloak = KeycloakTestContainer.getInstance();


    public String authToken(){

    }





    private URI




}
