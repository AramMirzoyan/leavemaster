package com.leave.master.leavemaster.dto.userdto.keycloak;

import com.leave.master.leavemaster.security.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder(toBuilder = true)
@Getter
public class KeycloakUserResponseDto {
  private String externalId;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @Email @NotBlank private String email;
  private boolean isEnabled;
  private Instant createdAt;
  @NotEmpty private Role role;
}
