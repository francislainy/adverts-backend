package com.example.adverts.model;

import com.google.gson.annotations.Expose;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class SubCategories
{
    @Expose
    private List<SubCategory> list;

    public List<SubCategory> getList() {
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}
