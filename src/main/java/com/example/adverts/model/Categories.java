package com.example.adverts.model;

import com.google.gson.annotations.Expose;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class Categories
{
    @Expose
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        if(categoryList == null) {
            categoryList = new ArrayList<>();
        }
        return categoryList;
    }

}
