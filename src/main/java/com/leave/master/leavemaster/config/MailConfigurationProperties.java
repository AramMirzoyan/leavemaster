package com.leave.master.leavemaster.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.mail")
public class MailConfigurationProperties {
  private String host;
  private int port;
  private String username;
  private String password;
}
