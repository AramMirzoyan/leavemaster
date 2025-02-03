package com.leave.master.leavemaster.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.PasswordUpdateDto;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.security.Role.Name;
import com.leave.master.leavemaster.service.UserEntityService;
import com.leave.master.leavemaster.validation.FieldValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "${user.tag.name}", description = "${user.tag.dsc}")
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
  @Operation(
      summary = "${user.summary.create}",
      description = "${user.dsc}",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = @Content(schema = @Schema(implementation = UserRequestDto.class)),
              useParameterTypeSchema = true,
              required = true),
      method = "${user.method}")
  @PostMapping("/add")
  @Secured(Name.R_SEC_ADMIN)
  public GenResponse<UserResponseDto> add(
      @RequestBody @Valid final UserRequestDto dto, BindingResult bindingResult) {
    fieldValidator.validateBodyField(bindingResult);
    var user = userEntityService.createUser(dto);
    return GenResponse.success(user.get());
  }

  /**
   * Updates the password for a given user.
   *
   * <p>Subclasses may override this method to add custom password update logic.
   *
   * @param userId The ID of the user whose password will be updated.
   * @param passwordDto The request body containing the new password.
   * @return A {@link GenResponse} containing a success message.
   */
  @Operation(
      summary = "${user.summary.update.password}",
      description = "${user.dsc.update.password}",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = @Content(schema = @Schema(implementation = PasswordUpdateDto.class)),
              useParameterTypeSchema = true,
              required = true),
      method = "${user.method}")
  @Secured({Name.R_SEC_ADMIN, Name.R_SEC_USER})
  @PutMapping("/{userId}/password")
  public GenResponse<String> updatePassword(
      @PathVariable final String userId, final @RequestBody PasswordUpdateDto passwordDto) {
    userEntityService.changePassword(userId, passwordDto.getPassword());
    return GenResponse.success();
  }
}
