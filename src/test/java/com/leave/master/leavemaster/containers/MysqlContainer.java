package com.leave.master.leavemaster.containers;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;


public class MysqlContainer extends MySQLContainer<MysqlContainer> {
    private static final String IMAGE_VERSION = "mysql:8.4.2";
    private static final Network SHARED_NETWORK = Network.newNetwork();
    private static MysqlContainer container;


    private MysqlContainer() {
        super(IMAGE_VERSION);
    }

    public static MysqlContainer getInstance() {
        if (container == null) {
            container = new MysqlContainer()
                    .withNetwork(SHARED_NETWORK)
                    .withNetworkAliases("leavemaster-mysql-db")
                    .withUsername("root")
                    .withPassword("root")
                    .withInitScript("init-databases.sql")
                    .withUrlParam("log-bin-trust-function-creators", "1")
                    .withUrlParam("host_cache_size", "0")
                    .waitingFor(Wait.forLogMessage(".*ready for connections.*", 1));
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }
}
