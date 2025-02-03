package com.leave.master.leavemaster.events.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.leave.master.leavemaster.events.EventHandlerFactory;
import com.leave.master.leavemaster.events.model.UserRegisterEvent;

import lombok.RequiredArgsConstructor;

/**
 * Listener for handling user registration events.
 *
 * <p>This class listens for {@link UserRegisterEvent} and triggers the corresponding event handler.
 *
 * <p>Subclasses may override {@code handleEvent} to add custom behavior.
 */
@Component
@RequiredArgsConstructor
public class UserRegistrationEventListener {

  private final EventHandlerFactory eventHandlerFactory;

  /**
   * Handles user registration events.
   *
   * <p>This method is triggered when a {@link UserRegisterEvent} occurs. It retrieves the
   * appropriate event handler from {@link EventHandlerFactory} and processes the event.
   *
   * <p>Subclasses may override this method to provide custom handling logic.
   *
   * @param eventModel The {@link UserRegisterEvent} that was triggered.
   */
  @EventListener
  public void handleEvent(final UserRegisterEvent eventModel) {
    eventHandlerFactory.getHandler(eventModel.getEventType()).handler(eventModel);
  }
}
