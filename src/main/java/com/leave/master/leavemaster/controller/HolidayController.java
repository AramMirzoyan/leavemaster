package com.leave.master.leavemaster.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.model.Holiday;
import com.leave.master.leavemaster.service.HolidayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
@Tag(name = "${holiday.tag.name}", description = "${holiday.tag.dsc}")
public class HolidayController {

  private final HolidayService holidayService;

  /**
   * @return GenResponse<List < Holiday>> * all holidays from the current year.
   */
  @Operation(
      summary = "${holiday.summary.create}",
      description = "${holiday.dsc}",
      method = "${holiday.get.method}")
  @GetMapping
  public GenResponse<List<Holiday>> getAllHolidayData() {
    return GenResponse.success(holidayService.getHoliday());
  }
}
