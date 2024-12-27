package com.leave.master.leavemaster.service;

import com.leave.master.leavemaster.model.Holiday;

import java.util.List;

/** Interface defining operations for managing holiday data. */
public interface HolidayService {
  /**
   * Updates the holiday data in the repository.
   *
   * @param holidays the list of {@link Holiday} objects to save or update.
   */
  void update(List<Holiday> holidays);

  /** Deletes all holiday data from the repository. */
  void deleteAll();

  /**
   * Retrieves all holidays from the repository.
   *
   * @return a list of {@link Holiday} objects.
   */
  List<Holiday> getHoliday();

  /**
   * Checks if holiday data exists in the repository.
   *
   * @return true if holiday data exists, false otherwise.
   */
  boolean isDataExistOnTable();
}
