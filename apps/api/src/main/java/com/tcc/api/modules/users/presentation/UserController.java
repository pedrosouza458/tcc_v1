package com.tcc.api.modules.users.presentation;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.api.modules.users.application.usecases.CreateUserUseCase;
import com.tcc.api.modules.users.application.usecases.GetUserByIdUseCase;
import com.tcc.api.modules.users.application.usecases.ListUsersByInstitutionUseCase;
import com.tcc.api.modules.users.domain.UserRole;
import com.tcc.api.modules.users.presentation.dtos.UserFilterRequestDTO;
import com.tcc.api.modules.users.presentation.dtos.UserRequestDTO;
import com.tcc.api.modules.users.presentation.dtos.UserResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final CreateUserUseCase createUserUseCase;
    private final ListUsersByInstitutionUseCase listUsersByInstitutionUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    @Override
      @PostMapping()
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody UserRequestDTO request) {
        createUserUseCase.execute(request.toInput(), UserRole.STUDENT);
        Map<String, String> response = Map.of("message", "User created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listUsersByInstitution(
            @RequestParam("institutionId") UUID institutionId,   
            @ParameterObject UserFilterRequestDTO filters,
            @ParameterObject @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        var usersPage = listUsersByInstitutionUseCase.execute(institutionId, filters.toInput(), pageable);
        Page<UserResponseDTO> response = usersPage.map(UserResponseDTO::new);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id) {
        var user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }
}
