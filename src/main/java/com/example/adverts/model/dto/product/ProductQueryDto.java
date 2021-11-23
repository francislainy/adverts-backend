package com.example.adverts.model.dto.product;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQueryDto implements Serializable {

    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private SubCategory subCategory;

}
