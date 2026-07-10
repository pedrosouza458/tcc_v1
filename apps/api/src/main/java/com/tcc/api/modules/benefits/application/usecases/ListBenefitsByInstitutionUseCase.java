package com.tcc.api.modules.benefits.application.usecases;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tcc.api.modules.benefits.application.dtos.BenefitFiltersInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListBenefitsByInstitutionUseCase {
    
    private final BenefitRepository benefitRepository;

    public Page<Benefit> execute(UUID institutionId, BenefitFiltersInput filters, Pageable pageable){
        return benefitRepository.findAllByInstitution(institutionId, filters, pageable);
    }
}
