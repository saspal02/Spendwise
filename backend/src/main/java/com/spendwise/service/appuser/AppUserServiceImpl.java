package com.spendwise.service.appuser;

import com.spendwise.dto.RegisterRequest;
import com.spendwise.exception.UserAlreadyExistsException;
import com.spendwise.model.AppUser;
import com.spendwise.repo.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {
        // Check whether user exists or not
        final var exists = appUserRepo.existsByEmail(request.email());
        if (exists) throw new UserAlreadyExistsException("Email/User: %s is already present.".formatted(request.email()));

        var newUser = AppUser.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        appUserRepo.save(newUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var appUser = appUserRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new User(appUser.getId(), appUser.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));


    }


}
