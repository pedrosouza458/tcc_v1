package com.tcc.api.modules.benefits.application.dtos;

import java.time.LocalTime;

import com.tcc.api.modules.benefits.domain.BenefitFrequency;

public record BenefitFiltersInput(
    String name,
    Boolean active,
    Integer expectedPaymentDay,
    LocalTime expectedPaymentTime,
    BenefitFrequency frequency
) {}
