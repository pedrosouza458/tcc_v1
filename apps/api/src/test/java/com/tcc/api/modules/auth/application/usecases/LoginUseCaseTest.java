package com.tcc.api.modules.auth.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tcc.api.modules.auth.application.dtos.LoginInput;
import com.tcc.api.modules.auth.application.dtos.LoginResponse;
import com.tcc.api.modules.auth.domain.TokenService;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.UserRole;
import com.tcc.api.modules.users.domain.exceptions.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private LoginInput loginInput;
    private User mockUser;

    @BeforeEach
    void setUp() {
        loginInput = new LoginInput("fulanosilva@ifsul.edu.br", "Password@123");
        mockUser = User.builder()
                .id(UUID.randomUUID())
                .name("Fulano da Silva")
                .email("fulanosilva@ifsul.edu.br")
                .password("hashed_password")
                .role(UserRole.STUDENT)
                .build();
    }

    @Test
    @DisplayName("Should login with success and return token")
    void shouldLoginWithSuccess() {
        when(userRepository.findByEmail(loginInput.email())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(loginInput.password(), mockUser.getPassword())).thenReturn(true);
        when(tokenService.generateToken(mockUser)).thenReturn("jwt_mocked_token");

        LoginResponse response = loginUseCase.execute(loginInput);

        assertNotNull(response);
        assertEquals("jwt_mocked_token", response.token());
        assertEquals("Fulano da Silva", response.name());
        assertEquals(UserRole.STUDENT, response.role());

        verify(tokenService).generateToken(mockUser);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user is not found")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(loginInput.email())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            loginUseCase.execute(loginInput);
        });

        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should throw BadCredentialsException when password does not match")
    void shouldThrowExceptionWhenPasswordDoesNotMatch() {
        when(userRepository.findByEmail(loginInput.email())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(loginInput.password(), mockUser.getPassword())).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> {
            loginUseCase.execute(loginInput);
        });

        verify(tokenService, never()).generateToken(any());
    }
}
