package com.spendwise.service.auth;

import com.spendwise.dto.AuthResponse;
import com.spendwise.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
