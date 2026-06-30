package com.tcc.api.modules.users.presentation;

import java.util.Map;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tcc.api.modules.users.presentation.dtos.UserFilterRequestDTO;
import com.tcc.api.modules.users.presentation.dtos.UserRequestDTO;
import com.tcc.api.modules.users.presentation.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users", description = "Endpoints para gerenciamento de usuários")
public interface UserAPI {

        @Operation(summary = "Cadastrar aluno", description = "Registra novos alunos vinculados a uma instituição.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso!"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
                        @ApiResponse(responseCode = "403", description = "Você não tem permissão para cadastrar alunos"),
                        @ApiResponse(responseCode = "409", description = "Conflito: E-mail ou CPF já cadastrados no sistema")
        })
        ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody UserRequestDTO request);

        @Operation(summary = "Listar alunos", description = "Retorna uma lista paginada dos alunos da instituição.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso"),
                        @ApiResponse(responseCode = "403", description = "Você não tem permissão para listar os alunos")
        })
        ResponseEntity<Page<UserResponseDTO>> listUsersByInstitution(
                        @RequestParam("institutionId") UUID institutionId,   
                        @ParameterObject UserFilterRequestDTO filters,
                        @ParameterObject @PageableDefault(page = 0, size = 20, sort = "name") Pageable pageable);

        @Operation(summary = "Buscar usuário por id", description = "Retorna dados de um usuário pelo id")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso!"),
                        @ApiResponse(responseCode = "403", description = "Você não tem permissão para visualizar esse usuário"),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        })
        ResponseEntity<UserResponseDTO> getUserById(
                        @PathVariable("id") @Parameter(name = "id", description = "ID do usuário", example = "a9db0a7f-cb21-4f2c-8536-227f81670604", required = true) UUID id);
}
