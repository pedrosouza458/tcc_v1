package com.tcc.api.modules.categories.infrastructure;

import com.tcc.api.modules.categories.domain.Category;
import com.tcc.api.modules.categories.infrastructure.CategoryEntity;

public final class CategoryMapper {
     private CategoryMapper(){}

    public static CategoryEntity toEntity(Category domain){
        if (domain == null) return null;
        return CategoryEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .colorCode(domain.getColorCode())
            .iconName(domain.getIconName())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .build();
    }

    public static Category toDomain(CategoryEntity entity){
        if (entity == null) return null;
        return Category.builder()
            .id(entity.getId())
            .name(entity.getName())
            .colorCode(entity.getColorCode())
            .iconName(entity.getIconName())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
