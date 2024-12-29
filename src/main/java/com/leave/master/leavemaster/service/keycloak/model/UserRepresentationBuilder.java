package com.leave.master.leavemaster.service.keycloak.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.representations.idm.*;

/**
 * A builder class for creating instances of {@link UserRepresentation}. This class provides a
 * fluent API for building {@link UserRepresentation} objects.
 */
public final class UserRepresentationBuilder extends UserRepresentation {

  private UserRepresentationBuilder() {}

  /**
   * Creates a new {@link Builder} instance.
   *
   * @return a new instance of {@link Builder}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder class for constructing {@link UserRepresentationBuilder} instances. This class is not
   * intended for subclassing.
   */
  public static class Builder {
    private final UserRepresentationBuilder instance;

    /** Initializes a new instance of the {@link Builder} class. */
    public Builder() {
      this.instance = new UserRepresentationBuilder();
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param self the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder self(String self) {
      this.instance.self = self;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param origin the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder origin(String origin) {
      this.instance.origin = origin;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param createdTimestamp the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder createdTimestamp(Long createdTimestamp) {
      this.instance.createdTimestamp = createdTimestamp;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param enabled the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder enabled(Boolean enabled) {
      this.instance.enabled = enabled;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param totp the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder totp(Boolean totp) {
      this.instance.totp = totp;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param federationLink the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder federationLink(String federationLink) {
      this.instance.federationLink = federationLink;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param serviceAccountClientId the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder serviceAccountClientId(String serviceAccountClientId) {
      this.instance.serviceAccountClientId = serviceAccountClientId;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param credentials the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder credentials(List<CredentialRepresentation> credentials) {
      this.instance.credentials = credentials;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param disableableCredentialTypes the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder disableableCredentialTypes(Set<String> disableableCredentialTypes) {
      this.instance.disableableCredentialTypes = disableableCredentialTypes;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param requiredActions the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder requiredActions(List<String> requiredActions) {
      this.instance.requiredActions = requiredActions;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param federatedIdentities the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder federatedIdentities(List<FederatedIdentityRepresentation> federatedIdentities) {
      this.instance.federatedIdentities = federatedIdentities;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param realmRoles the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder realmRoles(List<String> realmRoles) {
      this.instance.realmRoles = realmRoles;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param clientRoles the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder clientRoles(Map<String, List<String>> clientRoles) {
      this.instance.clientRoles = clientRoles;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param clientConsents the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder clientConsents(List<UserConsentRepresentation> clientConsents) {
      this.instance.clientConsents = clientConsents;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param notBefore the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder notBefore(Integer notBefore) {
      this.instance.notBefore = notBefore;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param id the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder id(String id) {
      this.instance.id = id;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param username the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder username(String username) {
      this.instance.username = username;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param firstName the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder firstName(String firstName) {
      this.instance.firstName = firstName;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param lastName the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder lastName(String lastName) {
      this.instance.lastName = lastName;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param email the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder email(String email) {
      this.instance.email = email;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param emailVerified the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder emailVerified(Boolean emailVerified) {
      this.instance.emailVerified = emailVerified;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param attributes the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder attributes(Map<String, List<String>> attributes) {
      this.instance.attributes = attributes;
      return this;
    }

    /**
     * Sets the self URL of the user representation.
     *
     * @param userProfileMetadata the self URL.
     * @return the current {@link Builder} instance.
     */
    public Builder userProfileMetadata(UserProfileMetadata userProfileMetadata) {
      this.instance.setUserProfileMetadata(userProfileMetadata);
      return this;
    }

    /**
     * Builds the {@link UserRepresentationBuilder} instance.
     *
     * @return the constructed {@link UserRepresentationBuilder} instance.
     */
    public UserRepresentationBuilder build() {
      return this.instance;
    }
  }
}
