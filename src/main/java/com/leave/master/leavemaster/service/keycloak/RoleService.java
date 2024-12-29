package com.leave.master.leavemaster.service.keycloak;

/**
 * Service interface for managing roles within the application. Provides operations to synchronize
 * roles between the application and external systems.
 */
public interface RoleService {
  /**
   * Synchronizes roles between the application and the external role management system.
   *
   * <p>Implementations should ensure that roles are correctly updated and consistent with the
   * external system.
   */
  void syncRoles();
}
