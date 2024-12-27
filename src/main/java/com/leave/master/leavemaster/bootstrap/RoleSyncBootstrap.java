package com.leave.master.leavemaster.bootstrap;

import com.leave.master.leavemaster.service.keycloak.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value = false)
@RequiredArgsConstructor
@Slf4j
public class RoleSyncBootstrap {
  private final RoleService roleService;

  @PostConstruct
  void init() {
    log.info("Start do sync roles");
    roleService.syncRoles();
    log.info("Finish do sync roles");
  }
}
