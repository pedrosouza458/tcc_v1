package com.tcc.api.modules.auth.application.dtos;

public record LoginInput(
    String email,
    String password
) {}
