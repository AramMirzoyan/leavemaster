package com.leave.master.leavemaster.dto.userdto;

import com.leave.master.leavemaster.security.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class RoleGroupDto {
  private String id;
  private String name;
  private List<Role> roles;
}
