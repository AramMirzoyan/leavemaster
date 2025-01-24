package com.leave.master.leavemaster.dto.userdto;

import com.leave.master.leavemaster.model.enums.UserRoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractUserDto {

  @NotBlank(message = "name is mandatory")
  private String name;

  @NotBlank(message = "surname is mandatory")
  private String surname;

  @Email(message = "email should be valid")
  @NotBlank(message = "mail is mandatory")
  //  @Pattern(
  //      regexp = "^[A-Za-z0-9._%+-]+@cognaize\\.com$",
  //      message = "Email must be from the domain cognaize.com")
  private String email;

  @NotBlank(message = "job title is mandatory")
  private String jobTitle;

  @NotNull(message = "role is mandatory") private UserRoleEnum role;
}
