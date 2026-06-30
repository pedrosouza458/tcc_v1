package com.tcc.api.modules.users.application.usecases;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tcc.api.modules.users.application.dtos.UserFiltersInput;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListUsersByInstitutionUseCase {

    private final UserRepository userRepository;

    public Page<User> execute(UUID institutionId, UserFiltersInput filters, Pageable pageable){
        return userRepository.findAllByInstitution(institutionId, filters, pageable);
    }
}

