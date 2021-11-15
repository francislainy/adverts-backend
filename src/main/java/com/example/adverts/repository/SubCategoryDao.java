package com.example.adverts.repository;

import com.example.adverts.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubCategoryDao
{

    public SubCategoriesOld getAllSubCategoriesForCategory(Long id)
    {
        CategoryDao categoryDao = new CategoryDao();
        CategoryOld c = categoryDao.getCategory(id);

        List<SubCategoryOld> subCategoryList = c.getSubCategoryList();

        SubCategoriesOld subCategories = new SubCategoriesOld();
        subCategories.getList().addAll(subCategoryList);

        return subCategories;

    }

}
