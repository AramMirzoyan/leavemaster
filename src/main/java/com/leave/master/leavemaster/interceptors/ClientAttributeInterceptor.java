package com.leave.master.leavemaster.interceptors;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

import static com.leave.master.leavemaster.interceptors.ClientAuthHeader.AUTH_CLIENT_APP_HEADER;
import static com.leave.master.leavemaster.interceptors.ClientAuthHeader.CLIENT_APP_ADMIN_VALUE;

@Component
@RequiredArgsConstructor
public class ClientAttributeInterceptor implements HandlerInterceptor {
  private final LeaveMasterSecurityProperties properties;

  @SuppressWarnings("all")
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    var authentication = SecurityContextHolder.getContext().getAuthentication();
    var prop = Objects.requireNonNull(properties.getKeycloak());

    if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
      String clientId = StringUtils.EMPTY;
      String clientSecret = StringUtils.EMPTY;

      if ((Objects.isNull(request.getHeader(AUTH_CLIENT_APP_HEADER.getValue()))
              || request.getHeader(AUTH_CLIENT_APP_HEADER.getValue()).equals("undefined"))
          || (request
              .getHeader(AUTH_CLIENT_APP_HEADER.getValue())
              .equals(CLIENT_APP_ADMIN_VALUE.getValue()))) {
        clientId = prop.getClientId();
        clientSecret = prop.getClientSecret();
      }
      request.setAttribute("clientId", clientId);
      request.setAttribute("clientSecret", clientSecret);
    }
    return true;
  }
}
