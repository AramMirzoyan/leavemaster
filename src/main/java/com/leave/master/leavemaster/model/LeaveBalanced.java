package com.leave.master.leavemaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave_balanced")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaveBalanced extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_entity_id")
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "leave_type_id")
  private LeaveTypeEntity leaveTypeEntity;

  @Column(name = "year")
  private int year;

  @Column(name = "used_days")
  private int usedDays;

  @Column(name = "remaining_days")
  private int remainingDays;
}
