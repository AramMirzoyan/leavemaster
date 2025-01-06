package com.leave.master.leavemaster.service.impl;

import com.leave.master.leavemaster.dto.leave.LeaveRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.security.AuthenticationFacade;
import com.leave.master.leavemaster.service.HolidayService;
import com.leave.master.leavemaster.service.UserEntityService;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.repository.LeaveBalancedRepository;
import com.leave.master.leavemaster.repository.LeaveEntityRepository;
import com.leave.master.leavemaster.repository.LeaveTypeRepository;
import com.leave.master.leavemaster.service.LeaveEntityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static java.time.Month.JULY;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultLeaveEntityService implements LeaveEntityService {

  private final LeaveEntityRepository leaveEntityRepository;
  private final LeaveBalancedRepository leaveBalancedRepository;
  private final LeaveTypeRepository leaveTypeRepository;
  private final AuthenticationFacade authenticationFacade;
  private final HolidayService holidayService;
  private final UserEntityService userEntityService;

  @Override
  public boolean create(final LeaveRequestDto requestDto) {

    Try<UserResponseDto> userResponseDto = userEntityService.findById(authenticationFacade.getCurrentUserId());


    calculationForUnpaidLeave(userResponseDto,requestDto);




    return false;
  }

  private void calculationForUnpaidLeave( final Try<UserResponseDto> userResponseDto, final LeaveRequestDto leaveRequestDto){

    LocalDateTime createdAt = userResponseDto.get().getCreatedAt();
    int currenYear = LocalDateTime.now().getYear();
    if (createdAt.getYear()==currenYear){
      boolean before = createdAt.isBefore(LocalDateTime.of(currenYear, JULY, 1, 0, 0));
      if (before){
      //  have 3 days for day off
        LocalDateTime startDate = leaveRequestDto.getStartDate();
        LocalDateTime endDate = leaveRequestDto.getEndDate();



      } else {
        // have 6 days for  day of
      }

    }

     // have 6 days for day of;



  }









}
