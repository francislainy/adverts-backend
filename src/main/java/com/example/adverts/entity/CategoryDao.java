package com.example.adverts.entity;

import com.example.adverts.model.Categories;
import com.example.adverts.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao
{
    private static Categories list = new Categories();

    static
    {
        list.getCategoryList().add(new Category(1, "Lokesh"));
        list.getCategoryList().add(new Category(2, "Alex"));
        list.getCategoryList().add(new Category(3, "David"));
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
