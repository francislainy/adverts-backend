package com.example.adverts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategoryOld {

    private Long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Product> productList;

}
