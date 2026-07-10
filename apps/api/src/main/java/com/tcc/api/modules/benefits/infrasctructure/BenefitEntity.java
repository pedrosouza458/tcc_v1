package com.tcc.api.modules.benefits.infrasctructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tcc.api.modules.benefits.domain.BenefitFrequency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "benefits")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "institution_id", nullable = false)
    private UUID institutionId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 50)
    @Column(name = "icon_name", nullable = false)
    private String iconName;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "expected_payment_day")
    private Integer expectedPaymentDay;

    @NotNull()
    @Column(name = "expected_payment_time", nullable = false)
    private LocalTime expectedPaymentTime;
    
    @NotNull()
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private BenefitFrequency frequency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
    
}
