package com.tcc.api.modules.benefits.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tcc.api.modules.benefits.application.dtos.BenefitUpdateInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNameAlreadyExistsException;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateBenefitUseCase {

    private final BenefitRepository benefitRepository;

    public Benefit execute(UUID id, UUID institutionId, BenefitUpdateInput input) {
        Benefit benefit = benefitRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new BenefitNotFoundException(id));

        if (!benefit.getName().equalsIgnoreCase(input.name()) &&
            benefitRepository.existsByNameAndInstitutionId(input.name(), institutionId)) {
            throw new BenefitNameAlreadyExistsException(input.name());
        }

        benefit.setName(input.name());
        benefit.setDescription(input.description());
        benefit.setIconName(input.iconName());
        benefit.setAmount(input.amount());
        benefit.setExpectedPaymentDay(input.expectedPaymentDay());
        benefit.setExpectedPaymentTime(input.expectedPaymentTime());
        benefit.setFrequency(input.frequency());

        return benefitRepository.save(benefit);
    }
}
