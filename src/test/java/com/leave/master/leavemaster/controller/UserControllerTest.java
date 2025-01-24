package com.leave.master.leavemaster.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.leave.master.leavemaster.LeavemasterApplicationTest;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.model.enums.UserStatus;
import com.leave.master.leavemaster.service.UserEntityService;
import com.leave.master.leavemaster.validation.FieldValidator;

import io.vavr.control.Try;

@DisplayName("UserController Test Cases")
@WebMvcTest(UserController.class)
@Import({FieldValidator.class, LeavemasterApplicationTest.TestConfig.class})
@ExtendWith(MockitoExtension.class)
class UserControllerTest extends LeavemasterApplicationTest {
  @MockBean private UserEntityService userEntityService;

  @DisplayName("Should create user successfully")
  @Test
  @WithMockUser(username = "admin", roles = "security.admin")
  void creatUserSuccess() throws Exception {
    // given
    UserRequestDto userRequestDto =
        UserRequestDto.builder()
            .name("user test")
            .surname("user testing")
            .email("testUser@example.com")
            .password("123456789")
            .jobTitle("test engineer")
            .role(UserRoleEnum.USER)
            .build();
    UserResponseDto userResponseDto =
        UserResponseDto.builder()
            .id(UUID.randomUUID().toString())
            .name(userRequestDto.getName())
            .surname(userRequestDto.getSurname())
            .email(userRequestDto.getEmail())
            .jobTitle(userRequestDto.getJobTitle())
            .role(userRequestDto.getRole())
            .status(UserStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

    // when
    Mockito.when(userEntityService.createUser(Mockito.any(UserRequestDto.class)))
        .thenReturn(Try.success(userResponseDto));

    // then
    getMockMvc()
        .perform(
            MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(userRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.email").value(userRequestDto.getEmail()))
        .andExpect(jsonPath("$.data.role").value("USER"))
        .andExpect(jsonPath("$.data.jobTitle").value(userResponseDto.getJobTitle()));
  }

  @DisplayName("Should return 403 Forbidden when user does not have the proper role!")
  @Test
  @WithMockUser(username = "user", roles = "security.user")
  void createUserForbidden() throws Exception {
    // given
    UserRequestDto userRequestDto =
        UserRequestDto.builder()
            .name("user test")
            .surname("user testing")
            .email("testUser@example.com")
            .password("123456789")
            .jobTitle("test engineer")
            .role(UserRoleEnum.USER)
            .build();

    // then
    getMockMvc()
        .perform(
            MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(userRequestDto)))
        .andExpect(status().isForbidden());
  }

  @DisplayName("Should return 412 Precondition Failed for invalid request body")
  @Test
  @WithMockUser(username = "user", roles = "security.admin")
  void createUserPreconditionFailed() throws Exception {
    // given
    UserRequestDto userRequestDto = UserRequestDto.builder().name("user test").build();

    // then
    getMockMvc()
        .perform(
            MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObjectMapper().writeValueAsString(userRequestDto)))
        .andExpect(status().isPreconditionFailed());
  }
}
