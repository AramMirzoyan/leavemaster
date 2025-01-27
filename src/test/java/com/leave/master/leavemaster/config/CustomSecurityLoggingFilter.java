package com.leave.master.leavemaster.config;

import java.io.IOException;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * A custom filter for logging security-related details about HTTP requests.
 *
 * <p>This filter logs the "Authorization" header if it is present and non-empty. Subclasses can
 * extend this filter to add more custom behavior if necessary. Ensure that the `doFilterInternal`
 * method is overridden carefully to preserve the intended behavior of this filter.
 */
@Slf4j
public final class CustomSecurityLoggingFilter extends OncePerRequestFilter {

  /**
   * Processes the request and response, logging the "Authorization" header if present.
   *
   * <p>Subclasses overriding this method should call {@code super.doFilterInternal()} to preserve
   * the base logging behavior, unless explicitly intended otherwise.
   *
   * @param request the HTTP servlet request
   * @param response the HTTP servlet response
   * @param filterChain the filter chain to proceed with the request
   * @throws ServletException if an error occurs during processing
   * @throws IOException if an I/O error occurs during processing
   */
  @Override
  @SuppressWarnings("all")
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (StringUtils.isNotBlank(token)) {
      log.info("correct token {}", token);
    } else {
      log.error("Token is null or empty {}", token);
    }

    filterChain.doFilter(request, response);
  }
}
