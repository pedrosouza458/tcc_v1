package com.tcc.api.modules.users.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.tcc.api.modules.users.application.dtos.UserFiltersInput;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.UserRole;

@ExtendWith(MockitoExtension.class)
public class ListUsersByInstitutionUseCaseTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private ListUsersByInstitutionUseCase listUsersByInstitutionUseCase;

        @Test
        @DisplayName("Should return a paginated list of users")
        void shouldReturnPaginatedListOfUsers() {
                UUID instutionId = UUID.randomUUID();

                UserFiltersInput filters = new UserFiltersInput(
                                UserRole.STUDENT,
                                "Fulano da Silva",
                                "fulanosilva@ifsul.edu.br",
                                "25695633079",
                                "51912341234");

                Pageable pageable = PageRequest.of(0, 20);

                User user = User.builder()
                                .id(UUID.randomUUID())
                                .name("Fulano da Silva")
                                .build();

                Page<User> mockPage = new PageImpl<>(List.of(user), pageable, 1);

                when(userRepository.findAllByInstitution(any(UUID.class), any(UserFiltersInput.class),
                                any(Pageable.class))).thenReturn(mockPage);

                Page<User> result = listUsersByInstitutionUseCase.execute(instutionId, filters, pageable);

                assertNotNull(result);
                assertEquals(1, result.getTotalElements());
                assertEquals("Fulano da Silva", result.getContent().get(0).getName());

                verify(userRepository).findAllByInstitution(
                                eq(instutionId), eq(filters), eq(pageable));
        }

        @Test
        @DisplayName("Should return an empty page when no users found")
        void shouldReturnEmptyPageWhenNoUsersFound() {
                UserFiltersInput filters = new UserFiltersInput(null,"Emptypage", null, null, null);
                Pageable pageable = PageRequest.of(0, 20);

                Page<User> emptyPage = Page.empty(pageable);

                when(userRepository.findAllByInstitution(any(), any(), any())).thenReturn(emptyPage);

                Page<User> result = listUsersByInstitutionUseCase.execute(null, filters, pageable);

                assertTrue(result.isEmpty());
                assertEquals(0, result.getTotalElements());
        }
    }