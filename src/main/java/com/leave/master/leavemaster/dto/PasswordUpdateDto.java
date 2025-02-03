package com.leave.master.leavemaster.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDto {
  private String password;
}
