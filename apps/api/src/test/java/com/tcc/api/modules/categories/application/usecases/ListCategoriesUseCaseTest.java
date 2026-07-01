package com.tcc.api.modules.categories.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.tcc.api.modules.categories.domain.Category;
import com.tcc.api.modules.categories.domain.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ListCategoriesUseCase listCategoriesUseCase;

    @Test
    @DisplayName("Should return list of categories")
    void shouldReturnListOfCategories() {
        // DADO
        var category1 = Category.builder()
                .id(UUID.randomUUID())
                .name("Alimentação")
                .colorCode("#FF5733")
                .iconName("utensils")
                .build();

        var category2 = Category.builder()
                .id(UUID.randomUUID())
                .name("Transporte")
                .colorCode("#3357FF")
                .iconName("car")
                .build();

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        // QUANDO
        List<Category> result = listCategoriesUseCase.execute();

        // ENTÃO
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alimentação", result.get(0).getName());
        assertEquals("Transporte", result.get(1).getName());
        
        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no categories exist")
    void shouldReturnEmptyListWhenNoCategoriesExist() {
        // DADO
        when(categoryRepository.findAll()).thenReturn(List.of());

        // QUANDO
        List<Category> result = listCategoriesUseCase.execute();

        // ENTÃO
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(categoryRepository).findAll();
    }
}
