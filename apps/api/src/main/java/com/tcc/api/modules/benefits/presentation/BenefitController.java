package com.tcc.api.modules.benefits.presentation;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.api.modules.benefits.application.usecases.ToggleBenefitUseCase;
import com.tcc.api.modules.benefits.application.usecases.CreateBenefitUseCase;
import com.tcc.api.modules.benefits.application.usecases.DeleteBenefitUseCase;
import com.tcc.api.modules.benefits.application.usecases.GetBenefitByIdUseCase;
import com.tcc.api.modules.benefits.application.usecases.ListBenefitsByInstitutionUseCase;
import com.tcc.api.modules.benefits.application.usecases.UpdateBenefitUseCase;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitCreateRequestDTO;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitFilterRequestDTO;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitResponseDTO;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitUpdateRequestDTO;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.exceptions.UserNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/benefits")
@RequiredArgsConstructor
public class BenefitController implements BenefitAPI {

    private final CreateBenefitUseCase createBenefitUseCase;
    private final ListBenefitsByInstitutionUseCase listBenefitsByInstitutionUseCase;
    private final GetBenefitByIdUseCase getBenefitByIdUseCase;
    private final UpdateBenefitUseCase updateBenefitUseCase;
    private final ToggleBenefitUseCase toggleBenefitUseCase;
    private final DeleteBenefitUseCase deleteBenefitUseCase;
    private final UserRepository userRepository;

    @Override
    @PostMapping
    public ResponseEntity<BenefitResponseDTO> createBenefit(
            @Valid @RequestBody BenefitCreateRequestDTO request,
            Principal principal
    ) {
        UUID institutionId = getInstitutionIdFromPrincipal(principal);
        var benefit = createBenefitUseCase.execute(institutionId, request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BenefitResponseDTO(benefit));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<BenefitResponseDTO>> listBenefits(
            BenefitFilterRequestDTO filters,
            Pageable pageable,
            Principal principal
    ) {
        UUID institutionId = getInstitutionIdFromPrincipal(principal);
        var filterInput = filters != null ? filters.toInput() : null;
        var benefitsPage = listBenefitsByInstitutionUseCase.execute(institutionId, filterInput, pageable);
        return ResponseEntity.ok(benefitsPage.map(BenefitResponseDTO::new));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BenefitResponseDTO> getBenefitById(
            @PathVariable("id") UUID id,
            Principal principal
    ) {
        UUID institutionId = getInstitutionIdFromPrincipal(principal);
        var benefit = getBenefitByIdUseCase.execute(id, institutionId);
        return ResponseEntity.ok(new BenefitResponseDTO(benefit));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<BenefitResponseDTO> updateBenefit(
            @PathVariable("id") UUID id,
            @Valid @RequestBody BenefitUpdateRequestDTO request,
            Principal principal
    ) {
        UUID institutionId = getInstitutionIdFromPrincipal(principal);
        var updated = updateBenefitUseCase.execute(id, institutionId, request.toInput());
        return ResponseEntity.ok(new BenefitResponseDTO(updated));
    }

    @Override
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Map<String, String>> toggleBenefitStatus(
            @PathVariable("id") UUID id,
            Principal principal
    ) {
        UUID institutionId = getInstitutionIdFromPrincipal(principal);
        toggleBenefitUseCase.execute(id, institutionId);
        return ResponseEntity.ok(Map.of("message", "Status do benefício alterado com sucesso"));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenefit(
            @PathVariable("id") UUID id,
            Principal principal
    ) {
        UUID institutionId = getInstitutionIdFromPrincipal(principal);
        deleteBenefitUseCase.execute(id, institutionId);
        return ResponseEntity.noContent().build();
    }

    private UUID getInstitutionIdFromPrincipal(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
        return user.getInstitutionId();
    }
}
