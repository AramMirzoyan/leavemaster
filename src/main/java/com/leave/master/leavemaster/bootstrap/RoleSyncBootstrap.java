package com.leave.master.leavemaster.bootstrap;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.leave.master.leavemaster.service.keycloak.RoleService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Bootstrap class responsible for initializing role synchronization during application startup.
 *
 * <p>This class triggers the role synchronization process by invoking the {@link
 * RoleService#syncRoles()} method.
 *
 * <p>If subclassing, ensure that the {@code init} method is overridden carefully to maintain the
 * integrity of the initialization process.
 */
@Component
@Lazy
@RequiredArgsConstructor
@Slf4j
public class RoleSyncBootstrap {
  private final RoleService roleService;

  /**
   * Initializes the role synchronization process.
   *
   * <p>This method is automatically invoked after the bean's dependencies are injected. Subclasses
   * overriding this method must ensure that role synchronization is performed correctly and log
   * messages are consistently handled.
   */
  @PostConstruct
  void init() {
    log.info("Start do sync roles");
    roleService.syncRoles();
    log.info("Finish do sync roles");
  }
}
