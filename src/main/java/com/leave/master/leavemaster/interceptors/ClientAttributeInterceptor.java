package com.leave.master.leavemaster.interceptors;

import static com.leave.master.leavemaster.interceptors.ClientAuthHeader.AUTH_CLIENT_APP_HEADER;
import static com.leave.master.leavemaster.interceptors.ClientAuthHeader.CLIENT_APP_ADMIN_VALUE;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Interceptor for setting client attributes in incoming HTTP requests.
 *
 * <p>This interceptor checks the authentication context and sets the "clientId" and "clientSecret"
 * attributes in the request if necessary.
 *
 * <p>If subclassing, ensure that the overridden {@code preHandle} method maintains the integrity of
 * client attribute handling and authentication checks.
 */
@Component
@RequiredArgsConstructor
public class ClientAttributeInterceptor implements HandlerInterceptor {
  private final LeaveMasterSecurityProperties properties;

  /**
   * Pre-handle method to process incoming requests before reaching the controller.
   *
   * <p>This method checks the authentication context and, for unauthenticated users, assigns the
   * clientId and clientSecret attributes to the request based on header values.
   *
   * <p>Subclasses overriding this method should ensure the request attributes are set appropriately
   * and security checks are not bypassed.
   *
   * @param request the incoming {@link HttpServletRequest}.
   * @param response the outgoing {@link HttpServletResponse}.
   * @param handler the chosen handler to execute.
   * @return {@code true} to proceed with the request, or {@code false} to abort further processing.
   * @throws Exception if an error occurs during processing.
   */
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
