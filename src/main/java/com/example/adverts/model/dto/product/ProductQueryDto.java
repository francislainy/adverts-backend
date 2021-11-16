package com.example.adverts.model.dto.product;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQueryDto implements Serializable {

    private UUID id;
    private String title;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "category"})
    private SubCategory subCategory;

    public ProductQueryDto(String title) {
        this.title = title;
    }

}
