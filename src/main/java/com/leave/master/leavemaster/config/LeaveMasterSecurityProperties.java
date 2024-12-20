package com.leave.master.leavemaster.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("leavemaster.security")
public class LeaveMasterSecurityProperties {

  private KeycloakProperties keycloak;

  @Getter
  @Setter
  public static class KeycloakProperties {
    private String uri;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String groupName;
    private String tokenEndPoint;
    private AdminProperties admin;

    @Getter
    @Setter
    public static class AdminProperties {
      private String realm;
      private String clientId;
      private String clientSecret;
      private String userName;
      private String password;
    }
  }
}
