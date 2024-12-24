package com.leave.master.leavemaster.security.filter;

import java.io.IOException;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leave.master.leavemaster.exceptiondendling.GlobalControllerExceptionHandler;
import com.leave.master.leavemaster.exceptiondendling.MethodArgumentException;
import com.leave.master.leavemaster.utils.Logging;
import com.leave.master.leavemaster.validation.FieldValidator;

import io.vavr.control.Try;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class LoginRequestValidationFilter extends OncePerRequestFilter {
  private static final String AUTH_PATH = "/auth/login";
  private static final String METHOD_POST = "POST";
  private final ObjectMapper objectMapper;
  private final FieldValidator fieldValidator;
  private final LocalValidatorFactoryBean validator;
  private final GlobalControllerExceptionHandler exceptionHandler;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
//    if (AUTH_PATH.equals(request.getServletPath()) && METHOD_POST.equals(request.getMethod())) {
//      Try.of(() -> objectMapper.readValue(request.getInputStream(), LoginRequestDtoTest.class))
//          .andThenTry(
//              loginRequestDto -> {
//                Logging.onInfo(() -> "Start do bind login data");
//                var dataBinder = new DataBinder(loginRequestDto);
//                dataBinder.setValidator(validator);
//                dataBinder.validate();
//                var bindingResult = dataBinder.getBindingResult();
//                fieldValidator.validateBodyField(bindingResult);
//                Logging.onInfo(() -> "Finish do bind login data");
//              })
//          .onFailure(MethodArgumentException.class, ex -> handleValidationError(response, ex));
////          .onSuccess(ignored -> proceedWithFilterChain(filterChain, request, response));
//    } else {
////      proceedWithFilterChain(filterChain, request, response);
//    }
//      proceedWithFilterChain(filterChain, request, response);
  }

  private void handleValidationError(HttpServletResponse response, MethodArgumentException ex) {
    Try.run(
            () -> {
              response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              var errorResponses = exceptionHandler.methodArgumentNotValidException(ex);
              response.getWriter().write(objectMapper.writeValueAsString(errorResponses));
            })
        .onFailure(th -> Logging.onFailed(() -> "Unexpected Errors ".concat(th.getMessage())));
  }
//
//  private void proceedWithFilterChain(
//      final FilterChain filterChain,
//      final HttpServletRequest request,
//      final HttpServletResponse response) {
//    Try.run(() -> filterChain.doFilter(request, response))
//        .onFailure(
//            error -> Logging.onFailed(() -> "Unexpected Errors ".concat(error.getMessage())));
//  }
}
