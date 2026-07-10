package com.tcc.api.modules.benefits.application.dtos;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.tcc.api.modules.benefits.domain.BenefitFrequency;

public record BenefitCreateInput(
   String name,
   String description,
   String iconName,
   BigDecimal amount,
   boolean active,
   Integer expectedPaymentDay,
   LocalTime expectedPaymentTime,
   BenefitFrequency frequency
) {}
