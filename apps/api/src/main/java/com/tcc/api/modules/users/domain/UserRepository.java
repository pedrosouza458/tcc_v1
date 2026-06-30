package com.tcc.api.modules.users.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tcc.api.modules.users.application.dtos.UserFiltersInput;

public interface UserRepository {
    User save(User user);
    Page<User> findAllByInstitution(UUID institutionId, UserFiltersInput input, Pageable pageable);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
}