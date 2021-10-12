package com.example.adverts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategory {

    private long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Product> productList;

}
