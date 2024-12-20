package com.leave.master.leavemaster.service.keycloak;

import java.util.List;
import java.util.Set;

import org.keycloak.representations.idm.UserRepresentation;

import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;

public interface KeycloakService {

  KeycloakUserResponseDto create(KeycloakUserRequestDto parameter);

  List<UserRepresentation> getAllUser();

  Set<String> getRolesByUserName(String username);
}
