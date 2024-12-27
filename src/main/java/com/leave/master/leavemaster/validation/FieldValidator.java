package com.leave.master.leavemaster.validation;

import com.leave.master.leavemaster.dto.erorresponse.ErrorResponse;
import com.leave.master.leavemaster.exceptiondendling.MethodArgumentException;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.utils.Logging;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Component
public class FieldValidator {

  public void validateBodyField(final BindingResult result) {
    Logging.onWarning(() -> "Start to validate fields if need to handle");
    if (result.hasErrors()) {
      Logging.onInfo(() -> "Fields has errors");
      final List<ErrorResponse> errors = new ArrayList<>();
      result
          .getAllErrors()
          .forEach(
              error ->
                  errors.add(
                      new ErrorResponse(
                          ((FieldError) error).getField(), error.getDefaultMessage())));

      throw new MethodArgumentException(ServiceErrorCode.BAD_REQUEST, errors);
    }
  }
}
