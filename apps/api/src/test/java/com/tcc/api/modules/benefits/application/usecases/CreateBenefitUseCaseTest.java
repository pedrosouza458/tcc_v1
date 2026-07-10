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
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcc.api.modules.benefits.application.dtos.BenefitCreateInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.domain.exceptions.BenefitNameAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class CreateBenefitUseCaseTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private CreateBenefitUseCase createBenefitUseCase;

    private BenefitCreateInput input;

    @BeforeEach
    void setup() {
        input = new BenefitCreateInput(
                "Auxílio Estudantil",
                "Benefício para alunos do IF",
                "general",
                BigDecimal.valueOf(700.00),
                true,
                1,
                LocalTime.now(),
                BenefitFrequency.MONTHLY);
    }

    @Test
    @DisplayName("Should create a benefit with success")
    void shouldCreateBenefitWithSuccess() {
        UUID institutionId = UUID.randomUUID();

        when(benefitRepository.existsByNameAndInstitutionId(input.name(), institutionId))
                .thenReturn(false);

        Benefit mockSavedBenefit = Benefit.builder()
                .id(UUID.randomUUID())
                .institutionId(institutionId)
                .name(input.name())
                .amount(input.amount())
                .active(input.active())
                .build();
        when(benefitRepository.save(any(Benefit.class))).thenReturn(mockSavedBenefit);

        Benefit result = createBenefitUseCase.execute(institutionId, input);

        assertNotNull(result);
        assertEquals(input.name(), result.getName());

        verify(benefitRepository, times(1)).save(any(Benefit.class));
    }

    @Test
    @DisplayName("Should throw a exception when name already exists")
    void shouldThrowExceptionWhenNameAlreadyExists() {
        UUID institutionId = UUID.randomUUID();

        when(benefitRepository.existsByNameAndInstitutionId(input.name(), institutionId))
                .thenReturn(true);

        assertThrows(BenefitNameAlreadyExistsException.class, () -> {
            createBenefitUseCase.execute(institutionId, input);
        });

        verify(benefitRepository, never()).save(any(Benefit.class));
    }
}
