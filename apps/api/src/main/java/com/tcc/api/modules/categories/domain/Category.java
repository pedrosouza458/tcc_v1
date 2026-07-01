package com.tcc.api.modules.categories.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private UUID id;
    private String name;
    private String colorCode;
    private String iconName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
