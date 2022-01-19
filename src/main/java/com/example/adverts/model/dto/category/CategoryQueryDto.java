package com.example.adverts.model.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryQueryDto implements Serializable {

    private UUID id;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long countSubCategories;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long countProducts;
}
