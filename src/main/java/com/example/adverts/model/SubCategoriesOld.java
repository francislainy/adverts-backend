package com.example.adverts.model;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class SubCategoriesOld
{
    private List<SubCategoryOld> list;

    public List<SubCategoryOld> getList() {
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
