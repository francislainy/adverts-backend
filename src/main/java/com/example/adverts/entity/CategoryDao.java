package com.example.adverts.entity;

import com.example.adverts.model.Categories;
import com.example.adverts.model.Category;
import com.example.adverts.model.SubCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CategoryDao
{
    private static Categories list = new Categories();
    private static ArrayList<SubCategory> subCategoryList1 = new ArrayList<>();
    private static SubCategory subCategory = new SubCategory(1,"Autographs");

    static
    {
        subCategoryList1.add(subCategory);
        list.getCategoryList().add(new Category(1, "Antiques & Collectables", subCategoryList1));
        list.getCategoryList().add(new Category(2, "Art & Crafts", null));
        list.getCategoryList().add(new Category(3, "Baby & Nursery", null));
    }




    public Categories getAllCategories()
    {
        return list;
    }

    public Category getCategory(Long id) {

        for (Category c: list.getCategoryList()) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public void addCategory(Category category) {
        list.getCategoryList().add(category);
    }
}
