package com.tcc.api.modules.users.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phone;
    private String profilePicture;
    private UUID institutionId;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
