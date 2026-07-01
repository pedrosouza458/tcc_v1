package com.tcc.api.modules.categories.domain;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
