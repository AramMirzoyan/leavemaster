package com.leave.master.leavemaster.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.model.Holiday;
import com.leave.master.leavemaster.repository.HolidayRepository;
import com.leave.master.leavemaster.service.HolidayService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public final class DefaultHolidayService implements HolidayService {
  private final HolidayRepository holidayRepository;

  /**
   * Updates the holiday data in the repository. Saves the provided list of holidays to the
   * database.
   *
   * @param holidays the list of {@link Holiday} objects to save.
   */
  @Override
  public void update(final List<Holiday> holidays) {
    holidayRepository.saveAll(holidays);
  }

  /** Deletes all holiday data from the repository. */
  @Override
  public void deleteAll() {
    holidayRepository.deleteAll();
  }

  /**
   * Retrieves all holidays from the repository, sorted by date in ascending order.
   *
   * @return a list of {@link Holiday} objects.
   */
  @Override
  public List<Holiday> getHoliday() {
    return holidayRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
  }

  /**
   * Checks if there is any data in the holiday table.
   *
   * @return true if there is at least one holiday in the table; false otherwise.
   */
  @Override
  public boolean isDataExistOnTable() {
    return holidayRepository.existsByIdNotNull();
  }
}
