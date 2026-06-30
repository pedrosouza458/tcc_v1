package com.tcc.api.modules.users.presentation.dtos;

import com.tcc.api.modules.users.application.dtos.UserFiltersInput;
import com.tcc.api.modules.users.domain.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserFilterRequestDTO(
    @Schema(example = "STUDENT")
    UserRole role,

    @Schema(example = "Fulano da Silva")
    String name,

    @Schema(example = "fulanosilva@if.edu.br")
    String email,

    @Schema(example = "20661123057")
    String cpf,

    @Schema(example = "51912341234")
    String phone
){
  public UserFiltersInput toInput(){
    return new UserFiltersInput(role, name, email, cpf, phone);
  }
}