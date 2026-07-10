package com.tcc.api.modules.benefits.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tcc.api.modules.benefits.application.dtos.BenefitCreateInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNameAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateBenefitUseCase {

    private final BenefitRepository benefitRepository;

    public Benefit execute(UUID institutionId, BenefitCreateInput input) {
        if (benefitRepository.existsByNameAndInstitutionId(input.name(), institutionId)) {
            throw new BenefitNameAlreadyExistsException(input.name());
        }
        var benefit = Benefit.builder()
                .institutionId(institutionId)
                .name(input.name())
                .description(input.description())
                .iconName(input.iconName())
                .amount(input.amount())
                .active(input.active())
                .expectedPaymentDay(input.expectedPaymentDay())
                .expectedPaymentTime(input.expectedPaymentTime())
                .frequency(input.frequency())
                .build();

        var savedBenefit = benefitRepository.save(benefit);

        return savedBenefit;
    }
}
