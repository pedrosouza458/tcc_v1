package com.tcc.api.modules.users.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public User execute(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
