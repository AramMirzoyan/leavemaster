package com.leave.master.leavemaster.model;

import com.leave.master.leavemaster.model.enums.LeaveStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Logs extends BaseEntity {
  @Column(name = "leave_id")
  private String leaveId;

  @Column(name = "leave_status")
  private LeaveStatus leaveStatus;

  @Column(name = "leave_type_id")
  private String leaveTypeId;

  @Column(name = "user_entity_id")
  private String userId;
}
