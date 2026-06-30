package com.tcc.api.modules.users.application.usecases;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.api.modules.users.application.dtos.CreateUserInput;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.UserRole;
import com.tcc.api.modules.users.domain.exceptions.CpfAlreadyExistsException;
import com.tcc.api.modules.users.domain.exceptions.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(CreateUserInput input, UserRole role) {
        if (userRepository.findByEmail(input.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.findByCpf(input.cpf()).isPresent()) {
            throw new CpfAlreadyExistsException();
        }

        String randomPassword = UUID.randomUUID().toString();

        var user = User.builder()
                .name(input.name())
                .email(input.email())
                .password(passwordEncoder.encode(randomPassword))
                .cpf(input.cpf())
                .phone(input.phone())
                .role(role)
                .institutionId(input.institutionId())
                .build();

        var savedUser = userRepository.save(user);

        return savedUser;
    }

}
