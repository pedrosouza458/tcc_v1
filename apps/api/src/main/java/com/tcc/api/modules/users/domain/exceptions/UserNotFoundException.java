package com.tcc.api.modules.users.domain.exceptions;

import java.util.UUID;

import com.tcc.api.shared.domain.exceptions.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(){
        super("Usuário não encontrado");
    }

    public UserNotFoundException(UUID id){
        super("Usuário não encontrado com id: " + id);
    }
}
