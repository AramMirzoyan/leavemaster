package com.leave.master.leavemaster.security.model;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;

@FunctionalInterface
public interface ApiClientRequest {

  HttpEntity<MultiValueMap<String, String>> get(LoginRequestDto dto);
}
