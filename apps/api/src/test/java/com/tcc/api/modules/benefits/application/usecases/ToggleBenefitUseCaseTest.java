package com.tcc.api.modules.benefits.application.usecases;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ToggleBenefitUseCaseTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private ToggleBenefitUseCase toggleBenefitUseCase;

    private UUID benefitId;
    private UUID institutionId;
    private Benefit mockBenefit;

    @BeforeEach
    void setup() {
        benefitId = UUID.randomUUID();
        institutionId = UUID.randomUUID();
        mockBenefit = Benefit.builder()
                .id(benefitId)
                .institutionId(institutionId)
                .name("Auxílio Refeição")
                .active(true)
                .build();
    }

    @Test
    @DisplayName("Should deactivate an active benefit successfully")
    void shouldDeactivateActiveBenefitSuccessfully() {
        mockBenefit.setActive(true);
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.of(mockBenefit));

        toggleBenefitUseCase.execute(benefitId, institutionId);

        assertFalse(mockBenefit.isActive());
        verify(benefitRepository, times(1)).save(mockBenefit);
    }

    @Test
    @DisplayName("Should activate an inactive benefit successfully")
    void shouldActivateInactiveBenefitSuccessfully() {
        mockBenefit.setActive(false);
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.of(mockBenefit));

        toggleBenefitUseCase.execute(benefitId, institutionId);

        assertTrue(mockBenefit.isActive());
        verify(benefitRepository, times(1)).save(mockBenefit);
    }

    @Test
    @DisplayName("Should throw exception when benefit to toggle does not exist")
    void shouldThrowExceptionWhenBenefitDoesNotExist() {
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.empty());

        assertThrows(BenefitNotFoundException.class, () -> {
            toggleBenefitUseCase.execute(benefitId, institutionId);
        });

        verify(benefitRepository, never()).save(any(Benefit.class));
    }
}
