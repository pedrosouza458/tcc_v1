package com.tcc.api.modules.benefits.infrasctructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitJpaRepository extends JpaRepository<BenefitEntity, UUID>, JpaSpecificationExecutor<BenefitEntity> {
    Optional<BenefitEntity> findByIdAndInstitutionId(UUID id, UUID institutionId);
    boolean existsByNameAndInstitutionId(String name, UUID institutionId);
}
