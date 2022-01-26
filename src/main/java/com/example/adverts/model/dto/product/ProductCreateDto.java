package com.example.adverts.model.dto.product;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreateDto implements Serializable {

    private UUID id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    @NotEmpty(message = "Short description cannot be empty")
    private String shortDescription;
    @NotNull(message = "Price cannot be empty")
    private BigDecimal price;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "countSubCategories", "products"})
    private Category category;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private SubCategory subCategory;

    public ProductCreateDto(String title, String description, String shortDescription, BigDecimal price, Category category, SubCategory subCategory) {
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.price = price;
        this.category = category;
        this.subCategory = subCategory;
    }
}
