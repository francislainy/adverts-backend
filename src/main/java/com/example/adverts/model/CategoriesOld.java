package com.example.adverts.model;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class CategoriesOld
{
    private List<CategoryOld> categoryList;

    public List<CategoryOld> getCategoryList() {
        if(categoryList == null) {
            categoryList = new ArrayList<>();
        }
        return categoryList;
    }

}
