package com.leave.master.leavemaster.events.handler.impl;

import org.springframework.stereotype.Component;

import com.leave.master.leavemaster.events.EventHandler;
import com.leave.master.leavemaster.events.model.UserRegisterEvent;
import com.leave.master.leavemaster.service.MailSenderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("CREATE_USER")
@SuppressWarnings("java:S6830")
public class UserRegistraionEventHandler implements EventHandler<UserRegisterEvent> {

  private final MailSenderService mailSenderService;

  /**
   * Handles the user registration event.
   *
   * <p>This method is triggered when a {@link UserRegisterEvent} occurs. It sends a registration
   * email with the user's credentials.
   *
   * <p>Subclasses may override this method to provide custom handling logic.
   *
   * @param eventModel The {@link UserRegisterEvent} containing user details.
   */
  @Override
  public void handler(final UserRegisterEvent eventModel) {
    final String subject = "Registration in LeaveMaster";
    final String text =
        """
        You are registered in our application with the following credentials.%n
        Username: %s%n
        Password: %s%n
        Please change your password after your first login.
        """
            .formatted(eventModel.getEmail(), eventModel.getPassword());
    mailSenderService.sendSimpleMessage(eventModel.getEmail(), subject, text);
  }
}
