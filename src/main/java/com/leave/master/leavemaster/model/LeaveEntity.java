package com.leave.master.leavemaster.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.leave.master.leavemaster.model.enums.LeaveStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LeaveEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_entity_id")
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "leave_type_id")
  private LeaveTypeEntity leaveTypeEntity;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "used_days")
  private int usedDays;

  @Column(name = "leave_status")
  private LeaveStatus leaveStatus;

  @Column(name = "requested_date")
  private LocalDateTime requestedDate;

  @Column(name = "message")
  private String message;

  @OneToMany(mappedBy = "leaveEntity", fetch = FetchType.LAZY)
  private Set<Bulletin> bulletins;
}
