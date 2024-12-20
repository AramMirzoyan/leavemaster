package com.leave.master.leavemaster.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.leave.master.leavemaster.interceptors.ClientAttributeInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMcvConfig implements WebMvcConfigurer {
  private final ClientAttributeInterceptor clientAttributeInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(clientAttributeInterceptor);
  }
}
