package com.example.adverts.entity;

import com.example.adverts.model.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SubCategoryDao
{

    public SubCategories getAllSubCategoriesForCategory(Long id)
    {
        CategoryDao categoryDao = new CategoryDao();
        Category c = categoryDao.getCategory(id);

        List<SubCategory> subCategoryList = c.getSubCategoryList();

        SubCategories subCategories = new SubCategories();
        subCategories.getList().addAll(subCategoryList);

        return subCategories;

    }

}
