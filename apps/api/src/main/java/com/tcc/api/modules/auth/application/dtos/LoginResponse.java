package com.tcc.api.modules.auth.application.dtos;

import com.tcc.api.modules.users.domain.UserRole;

public record LoginResponse(
    String token,
    String name,
    UserRole role
) {}
