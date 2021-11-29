package com.example.adverts.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreateDto implements Serializable {

    private UUID id;
    private String title;
    private String description;
    private String shortDescription;
    private BigDecimal price;

    private UUID categoryId;
    private UUID subCategoryId;

    public ProductCreateDto(String title, String description, String shortDescription, BigDecimal price, UUID categoryId, UUID subCategoryId) {
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.price = price;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
    }
}
