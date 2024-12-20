package com.leave.master.leavemaster.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leave.master.leavemaster.converter.requestdtoconverter.UserRequestDtoConvertToKeycloakUserRequestDto;
import com.leave.master.leavemaster.converter.roleconverter.SecurityRoleToUserRoleConverter;
import com.leave.master.leavemaster.converter.roleconverter.UserRoleToSecurityConverter;
import com.leave.master.leavemaster.converter.userentityconverter.EntityToUserDtoConverter;
import com.leave.master.leavemaster.converter.userentityconverter.KeycloakResponseDtoConvertToUserEntity;
import com.leave.master.leavemaster.converter.userentityconverter.UserDtoToEntityConverter;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserRequestDto;
import com.leave.master.leavemaster.dto.userdto.keycloak.KeycloakUserResponseDto;
import com.leave.master.leavemaster.model.UserEntity;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.security.Role;

/** Configuration class for defining bean converters for user-related entities and DTOs. */
@Configuration
public class ConverterConfig {
  /**
   * Defines a bean for converting {@link UserRequestDto} to {@link UserEntity}.
   *
   * @return a {@link Converter} that transforms a {@link UserRequestDto} into a {@link UserEntity}.
   */
  @Bean
  public Converter<UserRequestDto, UserEntity> userDtoToEntity() {
    return new UserDtoToEntityConverter();
  }

  /**
   * Defines a bean for converting {@link UserEntity} to {@link UserResponseDto}.
   *
   * @return a {@link Converter} that transforms a {@link UserEntity} into a {@link
   *     UserResponseDto}.
   */
  @Bean
  public Converter<UserEntity, UserResponseDto> entityToUserDto() {
    return new EntityToUserDtoConverter();
  }

  @Bean
  public Converter<Role, UserRoleEnum> securityRoleToUserRole() {
    return new SecurityRoleToUserRoleConverter();
  }

  @Bean
  public Converter<UserRoleEnum, Role> userRoleToSecurityRole() {
    return new UserRoleToSecurityConverter();
  }

  @Bean
  public Converter<UserRequestDto, KeycloakUserRequestDto>
      userRequestDtoKeycloakUserRequestDtoConverter() {
    return new UserRequestDtoConvertToKeycloakUserRequestDto();
  }

  @Bean
  public Converter<KeycloakUserResponseDto, UserEntity> keycloakResponseDtoConvertToUserEntity() {
    return new KeycloakResponseDtoConvertToUserEntity();
  }
}
