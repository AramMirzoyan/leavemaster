package com.leave.master.leavemaster.service.impl;

import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.repository.LeaveBalancedRepository;
import com.leave.master.leavemaster.repository.LeaveEntityRepository;
import com.leave.master.leavemaster.repository.LeaveTypeRepository;
import com.leave.master.leavemaster.service.LeaveEntityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultLeaveEntity implements LeaveEntityService {
  private final LeaveEntityRepository leaveEntityRepository;
  private final LeaveBalancedRepository leaveBalancedRepository;
  private final LeaveTypeRepository leaveTypeRepository;
}
