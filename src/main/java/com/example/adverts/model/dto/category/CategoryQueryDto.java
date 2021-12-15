package com.example.adverts.model.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryQueryDto implements Serializable {

    private UUID id;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long countSubCategories;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long countProducts;

    public CategoryQueryDto(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

}
