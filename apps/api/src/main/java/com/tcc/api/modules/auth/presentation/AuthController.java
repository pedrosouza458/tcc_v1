package com.tcc.api.modules.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.api.modules.auth.application.usecases.LoginUseCase;
import com.tcc.api.modules.auth.presentation.dtos.LoginRequestDTO;
import com.tcc.api.modules.auth.presentation.dtos.LoginResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthAPI {

    private final LoginUseCase loginUseCase;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        var response = loginUseCase.execute(request.toInput());
        return ResponseEntity.ok(new LoginResponseDTO(response));
    }
}
