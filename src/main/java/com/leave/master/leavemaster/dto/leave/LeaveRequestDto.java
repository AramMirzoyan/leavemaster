package com.leave.master.leavemaster.dto.leave;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String leaveType;
  private String message;
}
