package com.spendwise.service.appuser;

import com.spendwise.dto.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    void registerUser(RegisterRequest request);
}
