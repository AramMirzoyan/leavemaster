package com.leave.master.leavemaster.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.security.Role.Name;
import com.leave.master.leavemaster.service.UserEntityService;
import com.leave.master.leavemaster.validation.FieldValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserEntityService userEntityService;
  private final FieldValidator fieldValidator;

  /**
   * Creates a new user from the provided request data.
   *
   * @param dto the {@link UserRequestDto} containing the user's data.
   * @param bindingResult the {@link BindingResult} containing validation results for the provided
   *     request data.
   * @return a {@link GenResponse} containing the created user's details.
   */
  @PostMapping("/add")
  @Secured(Name.R_SEC_USER)
  public GenResponse<UserResponseDto> add(
      @RequestBody @Valid final UserRequestDto dto, BindingResult bindingResult) {
    fieldValidator.validateBodyField(bindingResult);
    var user = userEntityService.createUser(dto);
    return GenResponse.success(user.get());
  }
}
