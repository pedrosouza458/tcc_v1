package com.tcc.api.modules.users.domain.exceptions;

import com.tcc.api.shared.domain.exceptions.BusinessException;

public class CpfAlreadyExistsException extends BusinessException {
     public CpfAlreadyExistsException(){
        super("O CPF informado já está sendo usado.");
    }
}
