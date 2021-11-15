package com.example.adverts.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQueryDto {

    private UUID id;
    private String title;
    private UUID subCategoryId;
    private UUID categoryId;

    public ProductQueryDto(String title) {
        this.title = title;
    }

}
