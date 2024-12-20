package com.leave.master.leavemaster.security;

import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;

public interface AuthService {

  TokenResponseDto login(LoginRequestDto source);
}
