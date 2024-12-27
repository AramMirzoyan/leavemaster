package com.leave.master.leavemaster.security.model;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.util.function.Function;

public record DefaultApiClientRequest(
    Function<LoginRequestDto, HttpEntity<MultiValueMap<String, String>>> funHttpEntity)
    implements ApiClientRequest {

  @Override
  public HttpEntity<MultiValueMap<String, String>> get(LoginRequestDto dto) {
    return funHttpEntity.apply(dto);
  }
}
