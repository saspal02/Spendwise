package com.spendwise.controller;

import com.spendwise.dto.LoginRequest;
import com.spendwise.dto.RegisterRequest;
import com.spendwise.service.appuser.AppUserService;
import com.spendwise.service.auth.AuthService;
import com.spendwise.service.auth.UsernamePasswordAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Register the user with the provided registration details
        userService.registerUser(request);
        // Create a login request from the registered email and password
        final var loginRequest = new LoginRequest(request.email(), request.password());
        //Authenticate the user using the login credentials
        final var authResponse = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
