package com.leave.master.leavemaster.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave_type")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaveTypeEntity extends BaseEntity {
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;
}
