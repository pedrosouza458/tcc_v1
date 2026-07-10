package com.tcc.api.modules.benefits.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tcc.api.modules.benefits.application.dtos.BenefitFiltersInput;
import com.tcc.api.modules.benefits.domain.Benefit;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;
import com.tcc.api.modules.benefits.domain.BenefitRepository;

@ExtendWith(MockitoExtension.class)
public class ListBenefitsByInstitutionUseCaseTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private ListBenefitsByInstitutionUseCase listBenefitsByInstitutionUseCase;

    @Test
    @DisplayName("Should return a paginated list of benefits")
    void shouldReturnPaginatedListOfBenefits() {
        UUID institutionId = UUID.randomUUID();

        BenefitFiltersInput filters = new BenefitFiltersInput(
                "Auxílio",
                true,
                1,
                LocalTime.now(),
                BenefitFrequency.MONTHLY);

        Pageable pageable = PageRequest.of(0, 20);

        Benefit benefit = Benefit.builder()
                .id(UUID.randomUUID())
                .name("Auxílio Estudantil")
                .build();

        Page<Benefit> mockPage = new PageImpl<>(List.of(benefit), pageable, 1);

        when(benefitRepository.findAllByInstitution(any(UUID.class), any(BenefitFiltersInput.class),
                any(Pageable.class))).thenReturn(mockPage);

        Page<Benefit> result = listBenefitsByInstitutionUseCase.execute(institutionId, filters, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Auxílio Estudantil", result.getContent().get(0).getName());

        verify(benefitRepository).findAllByInstitution(
                eq(institutionId), eq(filters), eq(pageable));
    }

    @Test
    @DisplayName("Should return an empty page when no benefits found")
    void shouldReturnEmptyPageWhenNoBenefitsFound() {
        BenefitFiltersInput filters = new BenefitFiltersInput("Emptypage", null, null, null, null);
        Pageable pageable = PageRequest.of(0, 20);

        Page<Benefit> emptyPage = Page.empty(pageable);

        when(benefitRepository.findAllByInstitution(any(), any(), any())).thenReturn(emptyPage);

        Page<Benefit> result = listBenefitsByInstitutionUseCase.execute(null, filters, pageable);

        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}
