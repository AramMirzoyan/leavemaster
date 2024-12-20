package com.leave.master.leavemaster.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.converter.ConverterResolver;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.model.UserEntity;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.repository.UserEntityRepository;
import com.leave.master.leavemaster.repository.UserRoleRepository;
import com.leave.master.leavemaster.security.Role;
import com.leave.master.leavemaster.service.UserEntityService;
import com.leave.master.leavemaster.service.keycloak.impl.DefaultKeycloakService;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for managing user entities. Provides methods to create and manage user
 * data.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserEntityService implements UserEntityService {
  private final UserEntityRepository userEntityRepository;
  private final ConverterResolver converterResolver;
  private final DefaultKeycloakService keycloakService;
  private final UserRoleRepository userRoleRepository;

  /**
   * Creates a new user based on the provided {@link UserRequestDto}. Converts the request DTO to an
   * entity, updates entity fields, saves it to the repository, and converts it back to a response
   * DTO.
   *
   * @param userRequestDto the user data for creating a new user.
   * @return a {@link Try} containing the created {@link UserResponseDto} or an error.
   * @throws ServiceException if an unexpected error occurs during user creation.
   */
  @Override
  public Try<UserResponseDto> createUser(final UserRequestDto userRequestDto) {
    return converterResolver
        .convert(UserRequestDto.class, KeycloakUserRequestDto.class, userRequestDto)
        .andThen(
            keycloakUserRequestDto -> {
              if (userEntityRepository.existsByEmail(keycloakUserRequestDto.getEmail())) {
                throw new ServiceException(
                    ServiceErrorCode.BAD_REQUEST,
                    () ->
                        "User with email '%s' already exists"
                            .formatted(keycloakUserRequestDto.getEmail()));
              }
            })
        .map(
            keycloakUserRequestDto ->
                keycloakUserRequestDto.toBuilder()
                    .role(
                        converterResolver
                            .convert(UserRoleEnum.class, Role.class, userRequestDto.getRole())
                            .get())
                    .build())
        .map(keycloakService::create)
        .flatMap(
            userResponseDto ->
                converterResolver.convert(
                    KeycloakUserResponseDto.class, UserEntity.class, userResponseDto))
        .map(
            userEntity ->
                userEntity.toBuilder()
                    .jobTitle(userRequestDto.getJobTitle())
                    .userRole(
                        userRoleRepository
                            .findByName(userEntity.getUserRole().getName())
                            .orElseThrow(() -> new ServiceException(ServiceErrorCode.BAD_REQUEST)))
                    .build())
        .map(userEntityRepository::save)
        .map(
            userEntity ->
                converterResolver.convert(UserEntity.class, UserResponseDto.class, userEntity))
        .recover(this::recoverFromUnexpectedError)
        .getOrElseThrow(th -> new ServiceException(ServiceErrorCode.UNEXPECTED_ERROR));
  }

  @Override
  public Try<UserResponseDto> findUserByEmail(final String email) {

    //      return getRealmResource()
    //               .map(RealmResource::users)
    //               .map(users->users.searchByEmail(email,true))
    //               .map(List::getFirst)
    //               .map(user->user)

    return null;
  }

  @Override
  public Try<UserResponseDto> get(String id) {
    return null;
  }

  private <T> Try<T> recoverFromUnexpectedError(Throwable th) {
    if (th instanceof DataIntegrityViolationException) {
      return Try.failure(new ServiceException(ServiceErrorCode.DUPLICATE_ENTRY, th::getMessage));
    }
    if (th instanceof ServiceException ex) {
      if (ex.getErrorCode() == 400) {
        return Try.failure(new ServiceException(ServiceErrorCode.BAD_REQUEST, th::getMessage));
      }
    }
    return Try.failure(new ServiceException(ServiceErrorCode.CAN_NOT_CREATE_USER, th::getMessage));
  }
}
