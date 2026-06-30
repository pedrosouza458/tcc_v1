package com.tcc.api.modules.users.presentation.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDTO(

    @Schema(example = "a9db0a7f-cb21-4f2c-8536-227f81670604")
    UUID id,

    @Schema(example = "Fulano da Silva")
    String name,

    @Schema(example = "fulanosilva@if.edu.br")
    String email,

    @Schema(example = "37036080051")
    String cpf,

    @Schema(example = "51912341234")
    String phone,

    @Schema(example = "imgs/profile-picture.png")
    String profilePicture,

    @Schema(example = "STUDENT")
    UserRole role,

    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    UUID institutionId,

    @Schema(example = "2026-04-23T21:00:00")
    LocalDateTime createdAt
) {
    public UserResponseDTO(User user) {
        this(
            user.getId(),
            user.getName(), 
            user.getEmail(),
            user.getCpf(), 
            user.getPhone(), 
            user.getProfilePicture(), 
            user.getRole(), 
            user.getInstitutionId(),
            user.getCreatedAt() 
        );
    }
}
