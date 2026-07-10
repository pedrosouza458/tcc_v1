package com.tcc.api.modules.benefits.presentation;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tcc.api.modules.benefits.presentation.dtos.BenefitCreateRequestDTO;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitFilterRequestDTO;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitResponseDTO;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitUpdateRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Benefits", description = "Endpoints para gerenciamento de benefícios da instituição")
public interface BenefitAPI {

    @Operation(summary = "Cadastrar benefício", description = "Cria um novo benefício para a instituição do usuário autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Benefício criado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflito: Nome de benefício já cadastrado nesta instituição")
    })
    ResponseEntity<BenefitResponseDTO> createBenefit(
        @Valid @RequestBody BenefitCreateRequestDTO request,
        Principal principal
    );

    @Operation(summary = "Listar benefícios", description = "Retorna uma lista paginada de benefícios cadastrados na instituição do usuário.")
    ResponseEntity<Page<BenefitResponseDTO>> listBenefits(
        @ParameterObject BenefitFilterRequestDTO filters,
        @ParameterObject @PageableDefault(size = 20, sort = "name") Pageable pageable,
        Principal principal
    );

    @Operation(summary = "Buscar benefício por ID", description = "Retorna os detalhes de um benefício da instituição.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Benefício encontrado"),
        @ApiResponse(responseCode = "404", description = "Benefício não encontrado")
    })
    ResponseEntity<BenefitResponseDTO> getBenefitById(
        @PathVariable("id") @Parameter(name = "id", description = "ID do benefício", required = true) UUID id,
        Principal principal
    );

    @Operation(summary = "Atualizar benefício", description = "Atualiza os dados cadastrais gerais de um benefício.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Benefício atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Benefício não encontrado")
    })
    ResponseEntity<BenefitResponseDTO> updateBenefit(
        @PathVariable("id") UUID id,
        @Valid @RequestBody BenefitUpdateRequestDTO request,
        Principal principal
    );

    @Operation(summary = "Alternar status do benefício", description = "Ativa ou desativa um benefício.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Benefício não encontrado")
    })
    ResponseEntity<Map<String, String>> toggleBenefitStatus(
        @PathVariable("id") UUID id,
        Principal principal
    );

    @Operation(summary = "Deletar benefício", description = "Deleta permanentemente um benefício da instituição.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Benefício deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Benefício não encontrado")
    })
    ResponseEntity<Void> deleteBenefit(
        @PathVariable("id") UUID id,
        Principal principal
    );
}
