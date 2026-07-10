package com.tcc.api.modules.benefits.infrasctructure;

import com.tcc.api.modules.benefits.domain.Benefit;

public class BenefitMapper {
    private BenefitMapper() {
    }

    public static BenefitEntity toEntity(Benefit domain) {
        if (domain == null) return null;
        return BenefitEntity.builder()
                .id(domain.getId())
                .institutionId(domain.getInstitutionId())
                .name(domain.getName())
                .description(domain.getDescription())
                .iconName(domain.getIconName())
                .amount(domain.getAmount())
                .active(domain.isActive())
                .expectedPaymentDay(domain.getExpectedPaymentDay())
                .expectedPaymentTime(domain.getExpectedPaymentTime())
                .frequency(domain.getFrequency())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static Benefit toDomain(BenefitEntity entity) {
        if (entity == null) return null;
        return Benefit.builder()
                .id(entity.getId())
                .institutionId(entity.getInstitutionId())
                .name(entity.getName())
                .description(entity.getDescription())
                .iconName(entity.getIconName())
                .amount(entity.getAmount())
                .active(entity.isActive())
                .expectedPaymentDay(entity.getExpectedPaymentDay())
                .expectedPaymentTime(entity.getExpectedPaymentTime())
                .frequency(entity.getFrequency())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
