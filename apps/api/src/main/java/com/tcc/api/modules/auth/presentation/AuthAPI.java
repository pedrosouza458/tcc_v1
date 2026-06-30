package com.tcc.api.modules.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.tcc.api.modules.auth.presentation.dtos.LoginRequestDTO;
import com.tcc.api.modules.auth.presentation.dtos.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Endpoints para autenticação de usuários")
public interface AuthAPI {

    @Operation(summary = "Realizar login", description = "Autentica um usuário no sistema e retorna um token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (e-mail ou senha incorretos)")
    })
    ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request);
}
