package com.leave.master.leavemaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.Holiday;

/**
 * Repository interface for managing {@link Holiday} entities. Provides methods for CRUD operations
 * and custom queries.
 */
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, String> {

  /**
   * Checks if any {@link Holiday} entities exist in the database.
   *
   * @return true if there is at least one {@link Holiday} entity with a non-null ID, false
   *     otherwise.
   */
  boolean existsByIdNotNull();
}
