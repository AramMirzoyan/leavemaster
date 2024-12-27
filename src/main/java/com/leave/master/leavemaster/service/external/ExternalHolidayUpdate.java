package com.leave.master.leavemaster.service.external;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.model.Holiday;
import com.leave.master.leavemaster.service.impl.DefaultHolidayService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExternalHolidayUpdate {

  private final ExternalHolidayApiClient externalHolidayApiClient;
  private final DefaultHolidayService defaultHolidayService;

  /**
   * Initializes the holiday data update process. This method retrieves holiday data for the current
   * year and updates the database.
   */
  //    @PostConstruct
  public void initHolidayUpdate() {
    var currentYear = getCurrentYear();
    log.info("Current year: {}", currentYear);
    updateHolidayData(retrieveHolidayData(currentYear));
    log.info("FINISH to update holiday data");
  }

  /**
   * Scheduled task to update holiday data for the next year. Runs annually on December 31 at 10:30
   * PM, based on the configured cron expression and timezone.
   */
  @Scheduled(cron = "0 30 22 31 12 ?", zone = "${external.zone}")
  public void scheduleHolidayUsingCronExpression() {
    var currentYear = getCurrentYear();
    log.info("Current year: {}", currentYear);
    var nextYear = currentYear + 1;
    log.info("Next year: {}", nextYear);
    var nextYearHolidayData = retrieveHolidayData(nextYear);
    updateHolidayData(nextYearHolidayData);
    log.info("FINISH to update holiday data");
  }

  /**
   * Retrieves holiday data for a specific year.
   *
   * @param year the year for which holiday data is requested.
   * @return a list of {@link Holiday} objects.
   * @throws IllegalArgumentException if the year is 0 or negative.
   * @throws ServiceException if an error occurs while fetching data.
   */
  private List<Holiday> retrieveHolidayData(final int year) {
    log.info("START to get holiday data");
    handleFromUnexpectedError(year);
    return Try.of(() -> externalHolidayApiClient.getHolidayResponse(year))
        .getOrElseThrow(
            th -> {
              log.error("Error occurred while fetching holiday data: ");
              return new ServiceException(ServiceErrorCode.UNEXPECTED_ERROR, th::getMessage);
            });
  }

  /**
   * Gets the current year based on the system clock.
   *
   * @return the current year.
   */
  private int getCurrentYear() {
    return LocalDate.now().getYear();
  }

  /**
   * Updates the holiday data in the database. Deletes existing data if it exists before inserting
   * new data.
   *
   * @param holidays the list of holidays to update in the database.
   */
  private void updateHolidayData(final List<Holiday> holidays) {
    if (defaultHolidayService.isDataExistOnTable()) {
      defaultHolidayService.deleteAll();
      log.info("Deleted all existing data");
    }
    defaultHolidayService.update(holidays);
  }

  /**
   * Validates the year input to ensure it is positive and non-zero.
   *
   * @param year the year to validate.
   * @throws IllegalArgumentException if the year is 0 or negative.
   */
  private void handleFromUnexpectedError(final int year) {
    if (year == 0 || year < 0) {
      throw new IllegalArgumentException("The year can not be 0 or negative number");
    }
  }
}
