package com.example.adverts.model.dto.subcategory;

import com.example.adverts.model.entity.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryQueryDto implements Serializable {

    private UUID id;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long countProducts;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
    private Category category;

}
