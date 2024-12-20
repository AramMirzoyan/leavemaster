package com.leave.master.leavemaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bulletin")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bulletin extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_entity_id")
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "leave_entity_id")
  private LeaveEntity leaveEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "leave_type_id")
  private LeaveTypeEntity leaveTypeEntity;

  @Column(name = "url")
  private String url;
}
