package com.leave.master.leavemaster.events.model;

import com.leave.master.leavemaster.events.ListenersEventType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRegisterEvent implements EventModel {
  private ListenersEventType eventType;
  private String fullName;
  private String email;
  private String password;
  private String link;
}
