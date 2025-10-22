package com.iychay.be.auth.service;

import com.iychay.be.auth.dto.AuthResponse;
import com.iychay.be.auth.dto.LoginRequest;
import com.iychay.be.auth.dto.ProfileResponse;
import com.iychay.be.user.model.User;
import com.iychay.be.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;

    public DefaultAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // TODO: Implement JWT authentication with password verification
        return userRepository.findByEmail(request.email())
                .map(user -> new AuthResponse("pending-jwt-token", user.getRol(), user.getNombre()))
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
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
