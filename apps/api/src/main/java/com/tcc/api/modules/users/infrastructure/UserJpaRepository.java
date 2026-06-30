package com.tcc.api.modules.users.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcc.api.modules.users.domain.UserRole;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByCpf(String cpf);

   @Query("""
            SELECT u FROM UserEntity u
            WHERE u.institutionId = :institutionId
              AND (:role IS NULL OR u.role = :role)
              AND (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
              AND (:cpf IS NULL OR u.cpf LIKE CONCAT('%', :cpf, '%'))
              AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))
            """)
    Page<UserEntity> findByFilters(
            @Param("institutionId") UUID institutionId,
            @Param("role") UserRole role,
            @Param("name") String name,
            @Param("email") String email,
            @Param("cpf") String cpf,
            @Param("phone") String phone,
            Pageable pageable);
}

