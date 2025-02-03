package com.leave.master.leavemaster.exceptiondendling;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum ServiceErrorCode implements ErrorCode {
  PRECONDITION_FAILED(HttpStatus.PRECONDITION_FAILED, "Precondition Failed"),
  CAN_NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "Can not found "),
  LEAVE_VALIDATION(HttpStatus.BAD_REQUEST, StringUtils.EMPTY),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, StringUtils.EMPTY),
  DUPLICATE_ENTRY(HttpStatus.BAD_REQUEST, "Duplicate entry for email or other constraint violated"),
  CAN_NOT_CREATE_USER(HttpStatus.INTERNAL_SERVER_ERROR, "Can not create user"),
  CAN_NOT_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Can not send email"),
  UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");

  private final HttpStatus status;
  private final String message;

  @Override
  public String message() {
    return message;
  }

  @Override
  public HttpStatus getStatus() {
    return status;
  }
}
