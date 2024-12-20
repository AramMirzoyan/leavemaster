package com.leave.master.leavemaster.security.model;

import java.util.function.Function;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;

public record DefaultApiClientRequest(
    Function<LoginRequestDto, HttpEntity<MultiValueMap<String, String>>> funHttpEntity)
    implements ApiClientRequest {

  @Override
  public HttpEntity<MultiValueMap<String, String>> get(LoginRequestDto dto) {
    return funHttpEntity.apply(dto);
  }
}
