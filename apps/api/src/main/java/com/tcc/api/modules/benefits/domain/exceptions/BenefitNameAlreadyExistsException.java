package com.tcc.api.modules.benefits.domain.exceptions;

import com.tcc.api.shared.domain.exceptions.BusinessException;

public class BenefitNameAlreadyExistsException extends BusinessException {
    public BenefitNameAlreadyExistsException() {
        super("O nome informado já está sendo usado por outro benefício dessa instituição.");
    }

    public BenefitNameAlreadyExistsException(String name) {
        super("O nome" + name + "informado já está sendo usado por outro benefício dessa instituição.");
    }
}
