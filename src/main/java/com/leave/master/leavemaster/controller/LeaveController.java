package com.leave.master.leavemaster.controller;

import java.util.List;

import com.leave.master.leavemaster.dto.leave.LeaveRequestDto;
import com.leave.master.leavemaster.service.LeaveEntityService;
import com.leave.master.leavemaster.service.LeaveTypeService;
import org.springframework.web.bind.annotation.*;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.model.LeaveTypeEntity;
import com.leave.master.leavemaster.service.impl.DefaultLeaveTypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
@Slf4j
public class LeaveController {

  private final LeaveTypeService leaveTypeService;
  private final LeaveEntityService leaveEntityService;



  @PostMapping("/create")
  public GenResponse<Boolean> createLeave(@RequestBody final LeaveRequestDto requestDto){
   return  GenResponse.success(leaveEntityService.create(requestDto));
  }






  /**
   * fetch all type of leave type entity.
   *
   * @return a list of leave type entity
   */
  @GetMapping("/type")
  public GenResponse<List<LeaveTypeEntity>> getAll() {
    return GenResponse.success(leaveTypeService.getAll());
  }

  /**
   * fetch leave type entity by name.
   *
   * @param name leave type ('Annual Leave','Sick Leave','Unpaid Leave')
   * @return leave type entity
   */
  @GetMapping("/type/name/{name}")
  public GenResponse<LeaveTypeEntity> getLeaveTypeByName(@PathVariable final String name) {
    return GenResponse.success(leaveTypeService.getByName(name));
  }

  /**
   * fetch leave type entity by id.
   *
   * @param id leave type.
   * @return leave type entity
   */
  @GetMapping("/type/id/{id}")
  public GenResponse<LeaveTypeEntity> getLeaveTypeById(@PathVariable final String id) {
    return GenResponse.success(leaveTypeService.getById(id));
  }
}
