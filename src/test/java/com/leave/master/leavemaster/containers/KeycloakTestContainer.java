package com.leave.master.leavemaster.containers;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

public class KeycloakTestContainer extends KeycloakContainer {

  private static KeycloakTestContainer container;

  public KeycloakTestContainer() {
    super("quay.io/keycloak/keycloak:25.0.2");
    MysqlContainer mysqlContainer = MysqlContainer.getInstance();
    if (!mysqlContainer.isRunning()) {
      System.out.println("MySQL Container is not running. Starting...");
      mysqlContainer.start();
    }

    this.withNetwork(mysqlContainer.getNetwork())
            .withNetworkAliases("leavemaster-keycloak")
            .withEnv("KC_DB", "mysql")
            .withEnv("KC_DB_URL", String.format("jdbc:mysql://leavemaster-mysql-db:3306/%s", "keycloak_db"))
            .withEnv("KC_DB_USERNAME", "keycloaks")
            .withEnv("KC_DB_PASSWORD", "keycloak")
            .withEnv("KC_HOSTNAME_STRICT", "false")
            .withEnv("KC_HOSTNAME_STRICT_HTTPS", "false")
            .withEnv("KEYCLOAK_ADMIN", "admin")
            .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/")
                    .forPort(8080)
                    .withStartupTimeout(Duration.ofMinutes(5)));
  }

  public static KeycloakContainer getInstance() {
    if (container == null) {
      container = new KeycloakTestContainer();
    }
    return container.withRealmImportFile("keycloak/leavemaster.json");
  }
}
