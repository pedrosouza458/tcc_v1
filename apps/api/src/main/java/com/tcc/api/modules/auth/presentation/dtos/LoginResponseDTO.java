package com.tcc.api.modules.auth.presentation.dtos;

import com.tcc.api.modules.auth.application.dtos.LoginResponse;
import com.tcc.api.modules.users.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.ey...")
    String token,

    @Schema(example = "Fulano da Silva")
    String name,

    @Schema(example = "STUDENT")
    UserRole role
) {
    public LoginResponseDTO(LoginResponse response) {
        this(response.token(), response.name(), response.role());
    }
}
