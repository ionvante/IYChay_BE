package com.iychay.be.auth.service;

import com.iychay.be.auth.dto.AuthResponse;
import com.iychay.be.auth.dto.LoginRequest;
import com.iychay.be.auth.dto.ProfileResponse;
import com.iychay.be.auth.token.TokenService;
import com.iychay.be.user.model.User;
import com.iychay.be.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public DefaultAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = tokenService.generateToken(user);
        return new AuthResponse(token, user.getRol(), user.getNombre());
    }

    @Override
    public ProfileResponse me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Usuario no autenticado");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof User user) {
            return new ProfileResponse(user.getId(), user.getRol(), user.getNombre(), user.getEmail());
        }
        // TODO: Map authentication principal to domain user when JWT is in place
        return new ProfileResponse(null, null, authentication.getName(), null);
    }
}
