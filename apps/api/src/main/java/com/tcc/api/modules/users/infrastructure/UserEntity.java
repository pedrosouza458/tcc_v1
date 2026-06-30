package com.tcc.api.modules.users.infrastructure;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tcc.api.modules.users.domain.UserRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Size(min = 11, max = 11)
    @Column(name = "cpf", nullable = false, unique = true, length = 11, updatable = false)
    private String cpf;

    @Size(max = 15)
    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    @Column(name = "profile_picture", nullable = true)
    private String profilePicture;

    @Column(name = "institution_id", nullable = true, updatable = false)
    private UUID institutionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role; 

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
}
