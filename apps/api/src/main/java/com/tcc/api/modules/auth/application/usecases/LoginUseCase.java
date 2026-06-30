package com.tcc.api.modules.auth.application.usecases;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.api.modules.auth.application.dtos.LoginInput;
import com.tcc.api.modules.auth.application.dtos.LoginResponse;
import com.tcc.api.modules.auth.domain.TokenService;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse execute(LoginInput input) {
        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new BadCredentialsException("E-mail ou senha incorretos");
        }

        String token = tokenService.generateToken(user);
        return new LoginResponse(token, user.getName(), user.getRole());

    }
}
