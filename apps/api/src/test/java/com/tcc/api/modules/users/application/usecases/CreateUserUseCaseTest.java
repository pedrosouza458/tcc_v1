package com.tcc.api.modules.users.application.usecases;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tcc.api.modules.users.application.dtos.CreateUserInput;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.UserRole;
import com.tcc.api.modules.users.domain.exceptions.CpfAlreadyExistsException;
import com.tcc.api.modules.users.domain.exceptions.EmailAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private CreateUserInput input;

    @BeforeEach
    void setUp() {
        input = new CreateUserInput(
                "Fulano da Silva",
                "fulanosilva@ifsul.edu.br",
                "Password@123",
                "25695633079",
                "51912341234",
                "imgs/profile-picture.png",
                UUID.randomUUID());
    }

    @Test
    @DisplayName("Should create a user with success")
    void shouldCreateUserWithSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");

        User userSaved = User.builder().id(UUID.randomUUID()).email(input.email()).build();
        when(userRepository.save(any(User.class))).thenReturn(userSaved);

        User result = createUserUseCase.execute(input, UserRole.STUDENT);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw a exception when email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        when(userRepository.findByEmail(input.email())).thenReturn(Optional.of(new User()));
        
        assertThrows(EmailAlreadyExistsException.class, () -> {
            createUserUseCase.execute(input, UserRole.STUDENT);
        });

        verify(userRepository, never()).save(any());
    }

     @Test
    @DisplayName("Should throw a exception when cpf already exists")
    void shouldThrowExceptionWhenCPFExists() {
        when(userRepository.findByCpf(input.cpf())).thenReturn(Optional.of(new User()));
        
        assertThrows(CpfAlreadyExistsException.class, () -> {
            createUserUseCase.execute(input, UserRole.STUDENT);
        });

        verify(userRepository, never()).save(any());
    }

}
