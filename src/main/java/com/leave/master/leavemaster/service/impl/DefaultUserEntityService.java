package com.leave.master.leavemaster.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leave.master.leavemaster.converter.ConverterResolver;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.events.ListenersEventType;
import com.leave.master.leavemaster.events.model.UserRegisterEvent;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.model.UserEntity;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.repository.UserEntityRepository;
import com.leave.master.leavemaster.repository.UserRoleRepository;
import com.leave.master.leavemaster.security.Role;
import com.leave.master.leavemaster.service.UserEntityService;
import com.leave.master.leavemaster.service.keycloak.impl.DefaultKeycloakService;
import com.leave.master.leavemaster.utils.Utils;

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
  private final ApplicationEventPublisher eventPublisher;
  private final UserRoleRepository userRoleRepository;
  private static final int ERROR_CODE = 400;

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
  @Transactional
  public Try<UserResponseDto> createUser(final UserRequestDto userRequestDto) {
    return converterResolver
        .convert(UserRequestDto.class, KeycloakUserRequestDto.class, userRequestDto)
        .andThen(this::publishRegistration)
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

  /**
   * Finds a user by their email.
   *
   * @param email the email address of the user to find.
   * @return a {@link Try} containing the {@link UserResponseDto} or an error.
   */
  @Override
  public Try<UserResponseDto> findUserByEmail(final String email) {

    //      return getRealmResource()
    //               .map(RealmResource::users)
    //               .map(users->users.searchByEmail(email,true))
    //               .map(List::getFirst)
    //               .map(user->user)

    return null;
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve.
   * @return a {@link Try} containing the {@link UserResponseDto} or an error.
   */
  @Override
  public Try<UserResponseDto> findById(final String id) {
    return Try.of(
            () ->
                userEntityRepository
                    .findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceErrorCode.CAN_NOT_FOUND_DATA)))
        .flatMap(
            userEntity ->
                converterResolver.convert(UserEntity.class, UserResponseDto.class, userEntity));
  }

  /**
   * Changes the password for a user.
   *
   * <p>This method updates the user's password in Keycloak.
   *
   * <p>Subclasses may override this method to provide additional password change logic.
   *
   * @param userId The ID of the user whose password is being changed.
   * @param password The new password.
   * @return A {@link Try} representing success or failure.
   */
  @Override
  public Try<Void> changePassword(String userId, String password) {
    return Try.run((() -> keycloakService.changePassword(userId, password)));
  }

  /**
   * Handles recovery from unexpected errors.
   *
   * @param th the throwable causing the error.
   * @param <T> the type of the value being recovered.
   * @return a {@link Try} containing the error wrapped in a {@link ServiceException}.
   */
  private <T> Try<T> recoverFromUnexpectedError(final Throwable th) {
    if (th instanceof DataIntegrityViolationException) {
      return Try.failure(new ServiceException(ServiceErrorCode.DUPLICATE_ENTRY, th::getMessage));
    }
    if (th instanceof ServiceException ex && ex.getErrorCode()==ERROR_CODE) {
        return Try.failure(new ServiceException(ServiceErrorCode.BAD_REQUEST, th::getMessage));
    }
    return Try.failure(new ServiceException(ServiceErrorCode.UNEXPECTED_ERROR, th::getMessage));
  }

  private void publishRegistration(final KeycloakUserRequestDto entity) {
    eventPublisher.publishEvent(
        UserRegisterEvent.builder()
            .fullName(Utils.getFullName(entity.getFirstName(), entity.getLastName()))
            .email(entity.getEmail())
            .password(entity.getPassword())
            .eventType(ListenersEventType.CREATE_USER)
            .build());
  }
}
