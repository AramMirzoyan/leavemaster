package com.leave.master.leavemaster.service;

import java.util.List;

import com.leave.master.leavemaster.model.LeaveTypeEntity;

/**
 * Service interface for managing leave types. Provides methods to retrieve leave type entities by
 * various criteria.
 */
public interface LeaveTypeService {
  /**
   * Retrieves all leave type entities.
   *
   * @return a list of {@link LeaveTypeEntity} objects representing all leave types.
   */
  List<LeaveTypeEntity> getAll();

  /**
   * Retrieves a leave type entity by its unique identifier.
   *
   * @param id the unique identifier of the leave type.
   * @return the {@link LeaveTypeEntity} corresponding to the given ID.
   * @throws IllegalArgumentException if the ID is null or empty.
   */
  LeaveTypeEntity getById(String id);

  /**
   * Retrieves a leave type entity by its name.
   *
   * @param name the name of the leave type.
   * @return the {@link LeaveTypeEntity} corresponding to the given name.
   * @throws IllegalArgumentException if the name is null or empty.
   */
  LeaveTypeEntity getByName(String name);
}
