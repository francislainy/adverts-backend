package com.example.adverts.model.dto.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryCreateDto {

    private UUID id;
    private String title;
    private UUID categoryId;

    public SubCategoryCreateDto(String title, UUID categoryId) {
        this.title = title;
        this.categoryId = categoryId;
    }

}
