package com.iychay.be.auth.service;

import com.iychay.be.auth.dto.AuthResponse;
import com.iychay.be.auth.dto.LoginRequest;
import com.iychay.be.auth.dto.ProfileResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    ProfileResponse me(Authentication authentication);
}
