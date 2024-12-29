package com.leave.master.leavemaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.UserRole;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;

/**
 * Repository interface for managing {@link UserRole} persistence.
 *
 * <p>Provides methods for CRUD operations and custom queries related to {@link UserRole}.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
  /**
   * Finds a {@link UserRole} by its name.
   *
   * @param name the {@link UserRoleEnum} representing the role name to search for.
   * @return an {@link Optional} containing the {@link UserRole} if found, or empty if not found.
   */
  Optional<UserRole> findByName(UserRoleEnum name);
}
