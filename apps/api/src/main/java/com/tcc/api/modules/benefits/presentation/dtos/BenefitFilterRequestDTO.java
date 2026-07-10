package com.tcc.api.modules.benefits.presentation.dtos;

import java.time.LocalTime;

import com.tcc.api.modules.benefits.application.dtos.BenefitFiltersInput;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;

import io.swagger.v3.oas.annotations.media.Schema;

public record BenefitFilterRequestDTO(
    @Schema(example = "Vale")
    String name,

    @Schema(example = "true")
    Boolean active,

    @Schema(example = "10")
    Integer expectedPaymentDay,

    @Schema(example = "09:00:00")
    LocalTime expectedPaymentTime,

    @Schema(example = "MONTHLY")
    BenefitFrequency frequency
) {
    public BenefitFiltersInput toInput() {
        return new BenefitFiltersInput(
            name,
            active,
            expectedPaymentDay,
            expectedPaymentTime,
            frequency
        );
    }
}
