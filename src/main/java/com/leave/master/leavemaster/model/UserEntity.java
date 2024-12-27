package com.leave.master.leavemaster.model;

import java.time.LocalDateTime;

import com.leave.master.leavemaster.model.converter.UserStatusConverter;
import com.leave.master.leavemaster.model.enums.UserStatus;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_entity")
@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  @Id private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "status", nullable = false)
  @Setter
  @Convert(converter = UserStatusConverter.class)
  private UserStatus userStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_role_id")
  private UserRole userRole;

  @Column(name = "is_leave")
  @Builder.Default
  private boolean isLeave = false;

  @Column(name = "created_at", nullable = false)
  @Setter
  private LocalDateTime createdAt;

  @Column(name = "deactivated_at")
  @Setter
  private LocalDateTime deactivatedAt;

  @Column(name = "job_title")
  private String jobTitle;
}
