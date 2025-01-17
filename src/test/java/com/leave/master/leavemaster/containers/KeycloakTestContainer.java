package com.leave.master.leavemaster.containers;

import java.time.Duration;

import org.testcontainers.containers.wait.strategy.Wait;

import dasniko.testcontainers.keycloak.KeycloakContainer;

/** Initializes a KeycloakTestContainer instance configured for MySQL. */
public class KeycloakTestContainer extends KeycloakContainer {

  private static KeycloakTestContainer container;
  private static final int KEYCLOAK_PORT = 8080;
  private static final Duration STARTUP_TIMEOUT = Duration.ofMinutes(5);

  /**
   * Constructs a new KeycloakTestContainer instance configured for MySQL. It initializes a Keycloak
   * container with the necessary MySQL database configuration and network settings.
   */
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
        .withEnv(
            "KC_DB_URL", String.format("jdbc:mysql://leavemaster-mysql-db:3306/%s", "keycloak_db"))
        .withEnv("KC_DB_USERNAME", "keycloaks")
        .withEnv("KC_DB_PASSWORD", "keycloak")
        .withEnv("KC_HOSTNAME_STRICT", "false")
        .withEnv("KC_HOSTNAME_STRICT_HTTPS", "false")
        .withEnv("KEYCLOAK_ADMIN", "admin")
        .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
        .withExposedPorts(KEYCLOAK_PORT)
        .waitingFor(Wait.forHttp("/").forPort(KEYCLOAK_PORT).withStartupTimeout(STARTUP_TIMEOUT));
  }

  /**
   * Provides a singleton instance of KeycloakTestContainer.
   *
   * @return a configured KeycloakTestContainer instance.
   */
  public static KeycloakContainer getInstance() {
    if (container == null) {
      container = new KeycloakTestContainer();
    }
    return container.withRealmImportFile("keycloak/leavemaster.json");
  }
}
