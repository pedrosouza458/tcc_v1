package com.tcc.api.modules.benefits.presentation.dtos;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.tcc.api.modules.benefits.application.dtos.BenefitCreateInput;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BenefitCreateRequestDTO(
        @Schema(example = "Auxílio Mensal") @NotBlank(message = "O nome é obrigatório") @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres") String name,

        @Schema(example = "Auxílio mensal para estudantes") @NotBlank(message = "A descrição é obrigatória") @Size(max = 700, message = "A descrição deve ter no máximo 700 caracteres") String description,

        @Schema(example = "general") @NotBlank(message = "O ícone é obrigatório") @Size(max = 50, message = "O nome do ícone deve ter no máximo 50 caracteres") String iconName,

        @Schema(example = "700.00") @NotNull(message = "O valor é obrigatório") @Positive(message = "O valor deve ser maior que zero") BigDecimal amount,

        @Schema(example = "true") boolean active,

        @Schema(example = "10") Integer expectedPaymentDay,

        @Schema(example = "09:00:00") LocalTime expectedPaymentTime,

        @Schema(example = "MONTHLY") @NotNull(message = "A frequência é obrigatória") BenefitFrequency frequency) {
    public BenefitCreateInput toInput() {
        return new BenefitCreateInput(
                name,
                description,
                iconName,
                amount,
                active,
                expectedPaymentDay,
                expectedPaymentTime,
                frequency);
    }
}