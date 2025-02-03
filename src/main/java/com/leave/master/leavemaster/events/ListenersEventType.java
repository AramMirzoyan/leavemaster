package com.leave.master.leavemaster.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ListenersEventType {
  CREATE_USER("createUser");
  private final String name;
}
