package com.leave.master.leavemaster.model;

import com.leave.master.leavemaster.model.enums.LeaveStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApprovalEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_entity_id")
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "leave_id")
  private LeaveEntity leaveEntity;

  @Column(name = "status")
  private LeaveStatus leaveStatus;

  @Column(name = "message")
  private String message;
}
