package com.leave.master.leavemaster.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.leave.master.leavemaster.interceptors.ClientAttributeInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * Web MVC configuration for the LeaveMaster application.
 *
 * <p>Adds and manages custom interceptors for handling HTTP requests.
 */
@Configuration
@RequiredArgsConstructor
public class WebMcvConfig implements WebMvcConfigurer {
  private final ClientAttributeInterceptor clientAttributeInterceptor;

  /**
   * Adds interceptors to the application context.
   *
   * <p>Subclasses can override this method to customize the interceptor registry.
   *
   * @param registry the {@link InterceptorRegistry} to which interceptors are added.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(clientAttributeInterceptor);
  }
}
