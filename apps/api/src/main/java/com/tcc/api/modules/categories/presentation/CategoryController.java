package com.tcc.api.modules.categories.presentation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.api.modules.categories.application.usecases.ListCategoriesUseCase;
import com.tcc.api.modules.categories.presentation.dtos.CategoryResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryAPI {

    private final ListCategoriesUseCase listCategoriesUseCase;

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listCategories() {
        var categories = listCategoriesUseCase.execute();
        var response = categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();
        return ResponseEntity.ok(response);
    }
}
