package com.tcc.api.modules.benefits.application.usecases;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
public class DeleteBenefitUseCaseTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private DeleteBenefitUseCase deleteBenefitUseCase;

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
                .name("Auxílio Permanência")
                .build();
    }

    @Test
    @DisplayName("Should delete a benefit successfully")
    void shouldDeleteBenefitSuccessfully() {
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.of(mockBenefit));

        deleteBenefitUseCase.execute(benefitId, institutionId);

        verify(benefitRepository, times(1)).delete(mockBenefit);
    }

    @Test
    @DisplayName("Should throw exception when benefit to delete does not exist")
    void shouldThrowExceptionWhenBenefitDoesNotExist() {
        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.empty());

        assertThrows(BenefitNotFoundException.class, () -> {
            deleteBenefitUseCase.execute(benefitId, institutionId);
        });

        verify(benefitRepository, never()).delete(any(Benefit.class));
    }
}
