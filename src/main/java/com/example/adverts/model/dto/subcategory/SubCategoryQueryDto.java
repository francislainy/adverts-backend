package com.example.adverts.model.dto.subcategory;

import com.example.adverts.model.entity.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

}
