package com.leave.master.leavemaster.dto.userdto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto extends AbstractUserDto {
  @Builder.Default @JsonIgnore private String password = UUID.randomUUID().toString();
}
