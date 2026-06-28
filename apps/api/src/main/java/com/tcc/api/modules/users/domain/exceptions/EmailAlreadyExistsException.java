package com.tcc.api.modules.users.domain.exceptions;

import com.tcc.api.shared.domain.exceptions.BusinessException;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(){
        super("O e-mail informado já está sendo usado.");
    }
}
