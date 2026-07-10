package com.tcc.api.modules.benefits.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Benefit {
    private UUID id;
    private UUID institutionId;
    private String name;
    private String description;
    private String iconName;
    private BigDecimal amount;
    private boolean active;
    private Integer expectedPaymentDay;
    private LocalTime expectedPaymentTime;
    private BenefitFrequency frequency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
