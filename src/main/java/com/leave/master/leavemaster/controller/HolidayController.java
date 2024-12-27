package com.leave.master.leavemaster.controller;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.model.Holiday;
import com.leave.master.leavemaster.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
public class HolidayController {

  private final HolidayService holidayService;

  /**
   * @return GenResponse<List < Holiday>> * all holidays from the current year.
   */
  @GetMapping
  public GenResponse<List<Holiday>> getAllHolidayData() {
    return GenResponse.success(holidayService.getHoliday());
  }
}
