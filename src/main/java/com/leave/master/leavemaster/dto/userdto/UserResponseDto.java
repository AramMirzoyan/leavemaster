package com.leave.master.leavemaster.dto.userdto;

import java.time.LocalDateTime;

import com.leave.master.leavemaster.model.enums.UserStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto extends AbstractUserDto {

  private String id;
  private UserStatus status;
  private LocalDateTime createdAt;
}
