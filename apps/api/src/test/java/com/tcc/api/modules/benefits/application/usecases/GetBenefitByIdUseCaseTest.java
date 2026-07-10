package com.tcc.api.modules.benefits.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

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
public class GetBenefitByIdUseCaseTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private GetBenefitByIdUseCase getBenefitByIdUseCase;

    @Test
    @DisplayName("Should return a benefit when id exists")
    void shouldReturnBenefitWhenIdExists() {
        UUID institutionId = UUID.randomUUID();
        UUID benefitId = UUID.randomUUID();
        Benefit benefit = Benefit.builder()
                .id(benefitId)
                .name("Auxílio Estudantil")
                .build();

        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId))
                .thenReturn(Optional.of(benefit));

        Benefit result = getBenefitByIdUseCase.execute(benefitId, institutionId);

        assertNotNull(result);
        assertEquals(benefitId, result.getId());
        assertEquals("Auxílio Estudantil", result.getName());
        verify(benefitRepository, times(1)).findByIdAndInstitutionId(benefitId, institutionId);
    }

    @Test
    @DisplayName("Should throw error when benefit does not exists")
    void shouldThrowErrorWhenBenefitDoesNotExists() {
        UUID institutionId = UUID.randomUUID();
        UUID benefitId = UUID.randomUUID();

        when(benefitRepository.findByIdAndInstitutionId(benefitId, institutionId)).thenReturn(Optional.empty());

        assertThrows(BenefitNotFoundException.class, () -> {
            getBenefitByIdUseCase.execute(benefitId, institutionId);
        });

        verify(benefitRepository, times(1)).findByIdAndInstitutionId(benefitId, institutionId);
    }
}
