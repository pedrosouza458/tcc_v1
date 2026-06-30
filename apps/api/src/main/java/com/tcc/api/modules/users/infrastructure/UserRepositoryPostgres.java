package com.tcc.api.modules.users.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.tcc.api.modules.users.application.dtos.UserFiltersInput;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryPostgres implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user){
        var entity = UserMapper.toEntity(user);
        var saved = userJpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public Page<User> findAllByInstitution(UUID institutionId, UserFiltersInput filters, Pageable pageable){
        Page<UserEntity> usersPage = userJpaRepository.findByFilters(
            institutionId,
            filters.role(),
            filters.name(),
            filters.email(),
            filters.cpf(),
            filters.phone(),
            pageable
        );

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
