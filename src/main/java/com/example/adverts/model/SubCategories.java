package com.example.adverts.model;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class SubCategories
{
    private List<SubCategory> list;

    public List<SubCategory> getList() {
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
