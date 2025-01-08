package com.leave.master.leavemaster.containers;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class KeycloakTestContainer extends KeycloakContainer {

  private static KeycloakTestContainer container;

  public KeycloakTestContainer() {
    super("quay.io/keycloak/keycloak:25.0.2");
    MysqlContainer mysqlContainer = MysqlContainer.getInstance();
    mysqlContainer.start();

    this.withEnv("KC_DB", "mysql")
            .withEnv("KC_DB_URL_HOST", mysqlContainer.getHost())
            .withEnv("KC_DB_URL_PORT", String.valueOf(mysqlContainer.getMappedPort(3306)))
            .withEnv("KC_DB_USERNAME", "keycloaks")
            .withEnv("KC_DB_PASSWORD", "keycloak")
            .withEnv("KC_DB_URL_DATABASE", "keycloak_db")
            .withEnv("KC_HOSTNAME", "localhost")
            .withEnv("KC_HOSTNAME_STRICT", "false")
            .withEnv("KEYCLOAK_ADMIN", "admin")
            .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
            .withEnv("PROXY_ADDRESS_FORWARDING", "true")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/").forPort(8080).withStartupTimeout(java.time.Duration.ofSeconds(90)));
  }

  public static KeycloakContainer getInstance() {
    if (container == null) {
      container = new KeycloakTestContainer();
    }
    return container.withRealmImportFile("keycloak/leavemaster.json");
  }
}
