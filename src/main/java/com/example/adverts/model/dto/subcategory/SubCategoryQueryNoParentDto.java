package com.example.adverts.model.dto.subcategory;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryQueryNoParentDto implements Serializable {

    private UUID id;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long countProducts;
}
