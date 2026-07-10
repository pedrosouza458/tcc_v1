package com.tcc.api.modules.benefits.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tcc.api.modules.benefits.application.dtos.BenefitFiltersInput;

public interface BenefitRepository {
    Benefit save(Benefit benefit);
    Page<Benefit> findAllByInstitution(UUID institutionId, BenefitFiltersInput input, Pageable pageable);
    Optional<Benefit> findByIdAndInstitutionId(UUID id, UUID institutionId);
    boolean existsByNameAndInstitutionId(String name, UUID institutionId);
    void delete(Benefit benefit);
}
