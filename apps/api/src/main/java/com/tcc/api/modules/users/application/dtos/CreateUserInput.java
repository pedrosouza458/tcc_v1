package com.tcc.api.modules.users.application.dtos;

import java.util.UUID;

public record CreateUserInput(
    String name,
    String email,
    String password,
    String cpf,
    String phone,
    String profilePicture,
    UUID institutionId
){}
