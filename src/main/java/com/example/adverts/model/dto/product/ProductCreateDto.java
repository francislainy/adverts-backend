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
    private BigDecimal price;

    private UUID categoryId;
    private UUID subCategoryId;

    public ProductCreateDto(String title) {
        this.title = title;
    }

}
