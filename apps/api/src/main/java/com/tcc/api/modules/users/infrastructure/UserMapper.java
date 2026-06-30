package com.tcc.api.modules.users.infrastructure;

import com.tcc.api.modules.users.domain.User;

public final class UserMapper {
    private UserMapper(){}

    public static UserEntity toEntity(User domain){
        return UserEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .email(domain.getEmail())
            .password(domain.getPassword())
            .cpf(domain.getCpf())
            .phone(domain.getPhone())
            .profilePicture(domain.getProfilePicture())
            .role(domain.getRole())
            .institutionId(domain.getInstitutionId())
            .createdAt(domain.getCreatedAt())
            .build();
    }

    public static User toDomain(UserEntity entity){
        return User.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .cpf(entity.getCpf())
            .phone(entity.getPhone())
            .profilePicture(entity.getProfilePicture())
            .role(entity.getRole())
            .institutionId(entity.getInstitutionId())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
