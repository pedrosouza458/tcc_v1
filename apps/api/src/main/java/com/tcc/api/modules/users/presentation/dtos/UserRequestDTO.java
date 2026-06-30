package com.tcc.api.modules.users.presentation.dtos;

import java.util.UUID;

import com.tcc.api.modules.users.application.dtos.CreateUserInput;
import com.tcc.api.shared.infrastructure.validators.ValidCPF;
import com.tcc.api.shared.infrastructure.validators.ValidPassword;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @Schema(example = "Fulano da Silva")
        @NotBlank(message = "O nome é obrigatório") 
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres") 
        String name,

        @Schema(example = "fulanosilva@if.edu.br")
        @NotBlank(message = "O email é obrigatório") 
        @Email(message = "O email deve ser válido") 
        String email,

        @Schema(example = "Senha@123")
        @NotBlank(message = "A senha é obrigatória") 
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caractéres")
        @ValidPassword
        String password,

        @Schema(example = "20661123057")
        @NotBlank(message = "O CPF é obrigatório") 
        @ValidCPF
        String cpf,

        @Schema(example = "51912341234")
        @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres") 
        String phone,

        @Schema(example = "imgs/profile-picture.png")
        String profilePicture,

        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @NotNull(message = "O ID da instituição é obrigatório") 
        UUID institutionId
    ) {
    public CreateUserInput toInput() {
        return new CreateUserInput(name, email, password, cpf, phone, profilePicture, institutionId);
    }
}
