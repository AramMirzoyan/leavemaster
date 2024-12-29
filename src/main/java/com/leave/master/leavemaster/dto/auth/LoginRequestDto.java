package com.leave.master.leavemaster.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leave.master.leavemaster.security.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
public class LoginRequestDto {

  @NotBlank(message = "email is mandatory")
  private String email;

  @NotBlank(message = "password is mandatory")
  private String password;

  @JsonIgnore private String clientId;

  @JsonIgnore private String clientSecret;

  @JsonIgnore private Role role;
}
