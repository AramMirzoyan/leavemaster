package com.leave.master.leavemaster.model;

import com.leave.master.leavemaster.model.converter.UserRoleConverter;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRole {

  @Id private String id;

  @Column(name = "name", nullable = false)
  @Convert(converter = UserRoleConverter.class)
  private UserRoleEnum name;
}
