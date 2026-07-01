package com.tcc.api.modules.categories.presentation.dtos;

import java.util.UUID;
import com.tcc.api.modules.categories.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryResponseDTO(
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    UUID id,

    @Schema(example = "Alimentação")
    String name,

    @Schema(example = "#FF5733")
    String colorCode,

    @Schema(example = "utensils")
    String iconName
    ) {
        public CategoryResponseDTO(Category category) {
            this(
                category.getId(),
                category.getName(),
                category.getColorCode(),
                category.getIconName()
            );
        }
    }