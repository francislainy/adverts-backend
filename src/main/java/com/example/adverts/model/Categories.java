package com.example.adverts.model;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class Categories
{
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        if(categoryList == null) {
            categoryList = new ArrayList<>();
        }
        return categoryList;
    }

}
