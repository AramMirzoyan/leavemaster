package com.leave.master.leavemaster.dto.leave;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
