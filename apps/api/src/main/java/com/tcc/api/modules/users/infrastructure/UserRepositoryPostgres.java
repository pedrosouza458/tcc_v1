package com.tcc.api.modules.users.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tcc.api.modules.users.application.dtos.UserFiltersInput;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryPostgres implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        var entity = UserMapper.toEntity(user);
        var saved = userJpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public Page<User> findAllByInstitution(UUID institutionId, UserFiltersInput filters, Pageable pageable) {
        Specification<UserEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("institutionId"), institutionId));

            if (filters.role() != null) {
                predicates.add(cb.equal(root.get("role"), filters.role()));
            }
            if (StringUtils.hasText(filters.name())) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filters.name().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filters.email())) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filters.email().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(filters.cpf())) {
                predicates.add(cb.like(root.get("cpf"), "%" + filters.cpf() + "%"));
            }
            if (StringUtils.hasText(filters.phone())) {
                predicates.add(cb.like(root.get("phone"), "%" + filters.phone() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<UserEntity> usersPage = userJpaRepository.findAll(spec, pageable);
        return usersPage.map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return userJpaRepository.findByCpf(cpf).map(UserMapper::toDomain);
    }
}
