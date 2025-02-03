package com.leave.master.leavemaster.events;

import com.leave.master.leavemaster.events.model.EventModel;

/**
 * Generic interface for handling events.
 *
 * <p>This interface defines a contract for processing events of type {@link EventModel}.
 *
 * @param <T> The type of event that this handler processes.
 */
public interface EventHandler<T extends EventModel> {
  /**
   * Handles the given event.
   *
   * <p>Implementations of this method should define the processing logic for the specific event
   * type.
   *
   * @param eventModel The event to be handled.
   */
  void handler(T eventModel);
}
