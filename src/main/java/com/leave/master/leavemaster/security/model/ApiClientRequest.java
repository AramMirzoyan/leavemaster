package com.leave.master.leavemaster.security.model;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

@FunctionalInterface
public interface ApiClientRequest {

  HttpEntity<MultiValueMap<String, String>> get(LoginRequestDto dto);
}
