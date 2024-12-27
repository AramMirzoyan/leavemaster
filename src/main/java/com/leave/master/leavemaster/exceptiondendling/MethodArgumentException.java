package com.leave.master.leavemaster.exceptiondendling;

import com.leave.master.leavemaster.dto.erorresponse.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Supplier;

@Getter
public class MethodArgumentException extends ServiceException {
  public MethodArgumentException(
      HttpStatus status, String message, int errorCode, List<ErrorResponse> errorResponses) {
    super(status, message, errorCode, errorResponses);
  }

  public MethodArgumentException(ErrorCode code) {
    super(code);
  }

  public MethodArgumentException(ErrorCode code, Supplier<String> messageProvider) {
    super(code, messageProvider);
  }

  public MethodArgumentException(ErrorCode code, List<ErrorResponse> responses) {
    super(code, responses);
  }
}
