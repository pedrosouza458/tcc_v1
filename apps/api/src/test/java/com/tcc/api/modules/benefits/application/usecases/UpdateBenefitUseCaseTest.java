package com.tcc.api.modules.benefits.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcc.api.modules.benefits.application.dtos.BenefitUpdateInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNameAlreadyExistsException;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UpdateBenefitUseCaseTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private UpdateBenefitUseCase updateBenefitUseCase;

    private BenefitUpdateInput updateInput;
    private Benefit existingBenefit;
    private UUID benefitId;
    private UUID institutionId;

    @BeforeEach
    void setup() {
        benefitId = UUID.randomUUID();
        institutionId = UUID.randomUUID();

        updateInput = new BenefitUpdateInput(
            "Novo Nome do Benefício",
            "Nova Descrição",
            "new-icon",
            BigDecimal.valueOf(500.00),
            15,
            LocalTime.of(10, 0),
            BenefitFrequency.MONTHLY
        );

        existingBenefit = Benefit.builder()
                .id(benefitId)
                .institutionId(institutionId)
                .name("Nome Antigo")
                .description("Descrição Antiga")
                .iconName("old-icon")
                .amount(BigDecimal.valueOf(300.00))
                .active(true)
                .expectedPaymentDay(10)
                .expectedPaymentTime(LocalTime.of(8, 0))
                .frequency(BenefitFrequency.MONTHLY)
                .build();
    }

    @Test
    @DisplayName("Should update benefit details successfully")
    void shouldUpdateBenefitSuccessfully() {
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.of(existingBenefit));
        
        when(benefitRepository.existsByNameAndInstitutionId(updateInput.name(), institutionId))
                .thenReturn(false);

        when(benefitRepository.save(any(Benefit.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Benefit result = updateBenefitUseCase.execute(benefitId, institutionId, updateInput);

        assertNotNull(result);
        assertEquals(updateInput.name(), result.getName());
        assertEquals(updateInput.description(), result.getDescription());
        assertEquals(updateInput.amount(), result.getAmount());
        assertEquals(updateInput.expectedPaymentDay(), result.getExpectedPaymentDay());
        assertEquals(updateInput.expectedPaymentTime(), result.getExpectedPaymentTime());

        verify(benefitRepository, times(1)).save(any(Benefit.class));
    }

    @Test
    @DisplayName("Should throw exception when benefit to update does not exist")
    void shouldThrowExceptionWhenBenefitDoesNotExist() {
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.empty());

        assertThrows(BenefitNotFoundException.class, () -> {
            updateBenefitUseCase.execute(benefitId, institutionId, updateInput);
        });

        verify(benefitRepository, never()).save(any(Benefit.class));
    }

    @Test
    @DisplayName("Should throw exception when updated name already exists in institution")
    void shouldThrowExceptionWhenUpdatedNameAlreadyExists() {
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.of(existingBenefit));

        when(benefitRepository.existsByNameAndInstitutionId(updateInput.name(), institutionId))
                .thenReturn(true);

        assertThrows(BenefitNameAlreadyExistsException.class, () -> {
            updateBenefitUseCase.execute(benefitId, institutionId, updateInput);
        });

        verify(benefitRepository, never()).save(any(Benefit.class));
    }
}
