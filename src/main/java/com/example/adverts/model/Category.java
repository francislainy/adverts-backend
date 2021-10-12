package com.example.adverts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Category {

    private long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubCategory> subCategoryList;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
