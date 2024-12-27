package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.model.LeaveTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link LeaveTypeEntity} entities. Provides methods for CRUD
 * operations and custom queries.
 */
@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveTypeEntity, String> {

  /**
   * Finds a {@link LeaveTypeEntity} by its name.
   *
   * @param name the name of the leave type.
   * @return an {@link Optional} containing the {@link LeaveTypeEntity} if found, or empty if not.
   */
  Optional<LeaveTypeEntity> findByName(String name);
}
