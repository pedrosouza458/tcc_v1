package com.tcc.api.modules.benefits.infrasctructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tcc.api.modules.benefits.application.dtos.BenefitFiltersInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BenefitRepositoryPostgres implements BenefitRepository {
    
    private final BenefitJpaRepository benefitJpaRepository;

    @Override
    public Benefit save(Benefit benefit) {
        var entity = BenefitMapper.toEntity(benefit);
        var saved = benefitJpaRepository.save(entity);
        return BenefitMapper.toDomain(saved);
    }

    @Override
    public Optional<Benefit> findByIdAndInstitutionId(UUID id, UUID institutionId) {
        return benefitJpaRepository.findByIdAndInstitutionId(id, institutionId)
                .map(BenefitMapper::toDomain);
    }

    @Override
    public Page<Benefit> findAllByInstitution(UUID institutionId, BenefitFiltersInput filters, Pageable pageable) {
        Specification<BenefitEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("institutionId"), institutionId));

            if (filters != null) {
                if (StringUtils.hasText(filters.name())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + filters.name().toLowerCase() + "%"));
                }

                // Filtering by active status
                if (filters.active() != null) {
                    predicates.add(cb.equal(root.get("active"), filters.active()));
                }

                if (filters.expectedPaymentDay() != null) {
                    predicates.add(cb.equal(root.get("expectedPaymentDay"), filters.expectedPaymentDay()));
                }

                if (filters.expectedPaymentTime() != null) {
                    predicates.add(cb.equal(root.get("expectedPaymentTime"), filters.expectedPaymentTime()));
                }

                if (filters.frequency() != null) {
                    predicates.add(cb.equal(root.get("frequency"), filters.frequency()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return benefitJpaRepository.findAll(spec, pageable).map(BenefitMapper::toDomain);
    }

    @Override
    public boolean existsByNameAndInstitutionId(String name, UUID institutionId) {
        return benefitJpaRepository.existsByNameAndInstitutionId(name, institutionId);
    }

    @Override
    public void delete(Benefit benefit) {
        var entity = BenefitMapper.toEntity(benefit);
        benefitJpaRepository.delete(entity);
    }
}
