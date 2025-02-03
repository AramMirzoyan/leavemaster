package com.leave.master.leavemaster.events;

import com.leave.master.leavemaster.events.model.EventModel;

/**
 * Factory interface for retrieving event handlers.
 *
 * <p>This interface provides a method to obtain an {@link EventHandler} based on a given event
 * type.
 */
public interface EventHandlerFactory {
  /**
   * Retrieves an event handler for the specified event type.
   *
   * @param eventType The type of event for which a handler is needed.
   * @return An {@link EventHandler} instance capable of processing the specified event type.
   */
  EventHandler<EventModel> getHandler(ListenersEventType eventType);
}
