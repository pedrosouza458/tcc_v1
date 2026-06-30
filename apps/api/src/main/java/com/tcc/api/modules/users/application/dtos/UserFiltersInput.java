package com.tcc.api.modules.users.application.dtos;

import com.tcc.api.modules.users.domain.UserRole;

public record UserFiltersInput (
    UserRole role,
    String name,
    String email,
    String cpf,
    String phone
) {}