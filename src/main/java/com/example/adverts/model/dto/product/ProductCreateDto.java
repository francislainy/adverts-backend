package com.example.adverts.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreateDto {

    private UUID id;
    private String title;
    private UUID subCategoryId;
    private UUID categoryId;

    public ProductCreateDto(String title, UUID subCategoryId) {
        this.title = title;
        this.subCategoryId = subCategoryId;
    }

}
