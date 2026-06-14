package com.spendwise.service.auth;

import com.spendwise.config.JwtProps;
import com.spendwise.dto.AuthResponse;
import com.spendwise.dto.LoginRequest;
import com.spendwise.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsernamePasswordAuthService implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;
    private final JwtProps jwtProps;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Create unauthenticated email(username) and password token
        final var unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(request.email(),
                request.password());

        //Authenticated token
        final var authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);

        //Generate Access token & Refresh token
        // Extract email from authenticated token
        final var email = ((UserDetails) Objects.requireNonNull(authenticatedToken.getPrincipal())).getUsername();

        final Collection<? extends GrantedAuthority> roles = authenticatedToken.getAuthorities();

        final var expirationTimeAccessToken = jwtProps.getExpirationTimeAccessToken();
        final var accessToken = JwtUtils.generateAccessToken(email, roles, secretKey, expirationTimeAccessToken);

        return new AuthResponse(
                accessToken,
                "Bearer",
                expirationTimeAccessToken
        );
    }
}
