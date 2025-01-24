package com.leave.master.leavemaster.controller;

import com.leave.master.leavemaster.LeavemasterApplicationTests;
import com.leave.master.leavemaster.dto.userdto.UserRequestDto;
import com.leave.master.leavemaster.dto.userdto.UserResponseDto;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import com.leave.master.leavemaster.model.enums.UserStatus;
import com.leave.master.leavemaster.service.UserEntityService;
import com.leave.master.leavemaster.validation.FieldValidator;
import io.vavr.control.Try;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({FieldValidator.class})
@ExtendWith(MockitoExtension.class)
class UserControllerTest extends LeavemasterApplicationTests {
    @MockBean
    private UserEntityService userEntityService;


    @Test
    @WithMockUser(username = "admin", roles = "security.admin")
    public void creatUserSuccess() throws Exception {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("user test")
                .surname("user testing")
                .email("testUser@example.com")
                .password("123456789")
                .jobTitle("test engineer")
                .role(UserRoleEnum.USER)
                .build();
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(uuid)
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .email(userRequestDto.getEmail())
                .jobTitle(userRequestDto.getJobTitle())
                .role(userRequestDto.getRole())
                .status(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .build();
        Mockito.when(userEntityService.createUser(
                        Mockito.any(UserRequestDto.class)))
                .thenReturn(Try.success(userResponseDto));


        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(userRequestDto.getEmail()))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.jobTitle").value(userResponseDto.getJobTitle()));
    }
}

