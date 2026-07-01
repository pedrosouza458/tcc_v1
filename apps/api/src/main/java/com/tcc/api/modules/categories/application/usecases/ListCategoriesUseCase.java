package com.tcc.api.modules.categories.application.usecases;

import java.util.List;
import org.springframework.stereotype.Service;

import com.tcc.api.modules.categories.domain.Category;
import com.tcc.api.modules.categories.domain.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListCategoriesUseCase {
    
    private final CategoryRepository categoryRepository;

    public List<Category> execute() {
        return categoryRepository.findAll();
    }
}
