package com.tcc.api.modules.categories.presentation;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.tcc.api.modules.categories.presentation.dtos.CategoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categories", description = "Endpoints para consulta de categorias de despesas")
public interface CategoryAPI {

    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista contendo todas as categorias de despesas cadastradas no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    })
    ResponseEntity<List<CategoryResponseDTO>> listCategories();
}
