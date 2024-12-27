package com.leave.master.leavemaster.service.keycloak.model;

import org.keycloak.representations.idm.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class UserRepresentationBuilder extends UserRepresentation {

  private UserRepresentationBuilder() {}

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final UserRepresentationBuilder instance;

    public Builder() {
      this.instance = new UserRepresentationBuilder();
    }

    public Builder self(String self) {
      this.instance.self = self;
      return this;
    }

    public Builder origin(String origin) {
      this.instance.origin = origin;
      return this;
    }

    public Builder createdTimestamp(Long createdTimestamp) {
      this.instance.createdTimestamp = createdTimestamp;
      return this;
    }

    public Builder enabled(Boolean enabled) {
      this.instance.enabled = enabled;
      return this;
    }

    public Builder totp(Boolean totp) {
      this.instance.totp = totp;
      return this;
    }

    public Builder federationLink(String federationLink) {
      this.instance.federationLink = federationLink;
      return this;
    }

    public Builder serviceAccountClientId(String serviceAccountClientId) {
      this.instance.serviceAccountClientId = serviceAccountClientId;
      return this;
    }

    public Builder credentials(List<CredentialRepresentation> credentials) {
      this.instance.credentials = credentials;
      return this;
    }

    public Builder disableableCredentialTypes(Set<String> disableableCredentialTypes) {
      this.instance.disableableCredentialTypes = disableableCredentialTypes;
      return this;
    }

    public Builder requiredActions(List<String> requiredActions) {
      this.instance.requiredActions = requiredActions;
      return this;
    }

    public Builder federatedIdentities(List<FederatedIdentityRepresentation> federatedIdentities) {
      this.instance.federatedIdentities = federatedIdentities;
      return this;
    }

    public Builder realmRoles(List<String> realmRoles) {
      this.instance.realmRoles = realmRoles;
      return this;
    }

    public Builder clientRoles(Map<String, List<String>> clientRoles) {
      this.instance.clientRoles = clientRoles;
      return this;
    }

    public Builder clientConsents(List<UserConsentRepresentation> clientConsents) {
      this.instance.clientConsents = clientConsents;
      return this;
    }

    public Builder notBefore(Integer notBefore) {
      this.instance.notBefore = notBefore;
      return this;
    }

    public Builder id(String id) {
      this.instance.id = id;
      return this;
    }

    public Builder username(String username) {
      this.instance.username = username;
      return this;
    }

    public Builder firstName(String firstName) {
      this.instance.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.instance.lastName = lastName;
      return this;
    }

    public Builder email(String email) {
      this.instance.email = email;
      return this;
    }

    public Builder emailVerified(Boolean emailVerified) {
      this.instance.emailVerified = emailVerified;
      return this;
    }

    public Builder attributes(Map<String, List<String>> attributes) {
      this.instance.attributes = attributes;
      return this;
    }

    public Builder userProfileMetadata(UserProfileMetadata userProfileMetadata) {
      this.instance.setUserProfileMetadata(userProfileMetadata);
      return this;
    }

    public UserRepresentationBuilder build() {
      return this.instance;
    }
  }
}
