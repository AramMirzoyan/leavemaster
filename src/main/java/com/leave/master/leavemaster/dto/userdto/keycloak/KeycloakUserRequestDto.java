package com.leave.master.leavemaster.dto.userdto.keycloak;

import com.leave.master.leavemaster.security.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class KeycloakUserRequestDto {
  @NotBlank(message = "first name is mandatory")
  private String firstName;

  @NotBlank(message = "last name is mandatory")
  private String lastName;

  @NotBlank(message = "email is mandatory")
  private String email;

  @NotBlank(message = "password is mandatory")
  private String password;

  private Role role;
}
