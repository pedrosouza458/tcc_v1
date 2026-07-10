package com.tcc.api.modules.benefits.presentation.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;

import io.swagger.v3.oas.annotations.media.Schema;

public record BenefitResponseDTO(
    @Schema(example = "a9db0a7f-cb21-4f2c-8536-227f81670604")
    UUID id,

    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    UUID institutionId,

    @Schema(example = "Vale Alimentação")
    String name,

    @Schema(example = "Auxílio refeição mensal para estudantes")
    String description,

    @Schema(example = "utensils")
    String iconName,

    @Schema(example = "400.00")
    BigDecimal amount,

    @Schema(example = "true")
    boolean active,

    @Schema(example = "10")
    Integer expectedPaymentDay,

    @Schema(example = "09:00:00")
    LocalTime expectedPaymentTime,

    @Schema(example = "MONTHLY")
    BenefitFrequency frequency,

    @Schema(example = "2026-04-23T21:00:00")
    LocalDateTime createdAt,

    @Schema(example = "2026-04-23T21:00:00")
    LocalDateTime updatedAt
) {
    public BenefitResponseDTO(Benefit benefit) {
        this(
            benefit.getId(),
            benefit.getInstitutionId(),
            benefit.getName(),
            benefit.getDescription(),
            benefit.getIconName(),
            benefit.getAmount(),
            benefit.isActive(),
            benefit.getExpectedPaymentDay(),
            benefit.getExpectedPaymentTime(),
            benefit.getFrequency(),
            benefit.getCreatedAt(),
            benefit.getUpdatedAt()
        );
    }
}
