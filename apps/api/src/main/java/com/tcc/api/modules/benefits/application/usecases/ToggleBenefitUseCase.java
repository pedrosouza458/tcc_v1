package com.tcc.api.modules.benefits.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToggleBenefitUseCase {
    
    private final BenefitRepository benefitRepository;

    public void execute(UUID id, UUID institutionId){
      Benefit benefit = benefitRepository.findByIdAndInstitutionId(id, institutionId)
      .orElseThrow(() -> new BenefitNotFoundException());

      benefit.setActive(!benefit.isActive());
      benefitRepository.save(benefit);
    }
}
