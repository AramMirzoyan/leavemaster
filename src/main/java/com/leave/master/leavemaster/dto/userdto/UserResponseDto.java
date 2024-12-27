package com.leave.master.leavemaster.dto.userdto;

import com.leave.master.leavemaster.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
