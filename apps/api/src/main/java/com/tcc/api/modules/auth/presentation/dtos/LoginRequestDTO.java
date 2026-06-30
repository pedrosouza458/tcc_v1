package com.tcc.api.modules.auth.presentation.dtos;

import com.tcc.api.modules.auth.application.dtos.LoginInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @Schema(example = "fulanosilva@if.edu.br")
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    String email,

    @Schema(example = "Senha@123")
    @NotBlank(message = "A senha é obrigatória")
    String password
) {
    public LoginInput toInput() {
        return new LoginInput(email, password);
    }
}
