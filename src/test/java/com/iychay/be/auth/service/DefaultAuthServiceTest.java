package com.iychay.be.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.iychay.be.auth.dto.AuthResponse;
import com.iychay.be.auth.dto.LoginRequest;
import com.iychay.be.auth.token.TokenService;
import com.iychay.be.user.model.Role;
import com.iychay.be.user.model.User;
import com.iychay.be.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    private DefaultAuthService authService;

    @BeforeEach
    void setUp() {
        authService = new DefaultAuthService(userRepository, passwordEncoder, tokenService);
    }

    @Test
    void loginReturnsTokenWhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("user@example.com", "plainPassword");

        User user = new User();
        user.setId(1L);
        user.setEmail(request.email());
        user.setPasswordHash("encodedPassword");
        user.setNombre("Nombre Usuario");
        user.setRol(Role.PROFESOR);

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.password(), user.getPasswordHash())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertThat(response.token()).isEqualTo("jwt-token");
        assertThat(response.rol()).isEqualTo(user.getRol());
        assertThat(response.nombre()).isEqualTo(user.getNombre());

        verify(tokenService).generateToken(user);
    }

    @Test
    void loginThrowsExceptionWhenPasswordDoesNotMatch() {
        LoginRequest request = new LoginRequest("user@example.com", "wrongPassword");

        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash("encodedPassword");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.password(), user.getPasswordHash())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Credenciales inválidas");

        verifyNoInteractions(tokenService);
    }
}
