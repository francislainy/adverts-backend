package com.example.adverts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Category {

    private long id;
    private String name;
    private List<SubCategory> subCategoryList;

}
