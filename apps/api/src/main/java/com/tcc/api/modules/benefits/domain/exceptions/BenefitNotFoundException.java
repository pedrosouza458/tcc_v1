package com.tcc.api.modules.benefits.domain.exceptions;

import java.util.UUID;

import com.tcc.api.shared.domain.exceptions.BusinessException;

public class BenefitNotFoundException extends BusinessException {
    public BenefitNotFoundException() {
        super("Benefício não encontrado");
    }

    public BenefitNotFoundException(UUID id) {
        super("Benefício não encontrado com id: " + id);
    }
}
