package com.leave.master.leavemaster.controller.auth;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.security.AuthService;
import com.leave.master.leavemaster.validation.FieldValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class Authentication {
  private final FieldValidator validator;
  private final AuthService authService;

  @PostMapping("/login")
  public GenResponse<TokenResponseDto> login(
      @RequestBody @Valid final LoginRequestDto source,
      final HttpServletRequest request,
      final BindingResult result) {
    validator.validateBodyField(result);
    return GenResponse.success(
        authService.login(
            source.toBuilder()
                .clientId((String) request.getAttribute("clientId"))
                .clientSecret((String) request.getAttribute("clientSecret"))
                .build()),
        () -> "Login request processed successfully");
  }
}