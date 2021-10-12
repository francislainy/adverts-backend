package com.example.adverts.entity;

import com.example.adverts.model.Categories;
import com.example.adverts.model.Category;
import com.example.adverts.model.Product;
import com.example.adverts.model.SubCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao
{
    public static final Categories list = new Categories();
    public static final ArrayList<SubCategory> subCategoryList1 = new ArrayList<>();
    public static Product product = new Product(1, "prod name", "desc", "http", 10.0, "dublin");
    public static List<Product> productList = new ArrayList<>();

    public static final SubCategory subCategory = new SubCategory(1,"Autographs", productList);
    public static final SubCategory subCategory2 = new SubCategory(2,"Bottles", null);


    public static final ArrayList<SubCategory> subCategoryArtsList = new ArrayList<>();
    public static final SubCategory subCategoryArts = new SubCategory(1,"Art Supplies", null);
    public static final SubCategory subCategoryArts2 = new SubCategory(2,"Artisan Food", null);

    static
    {
        productList.add(product);
        subCategoryList1.add(subCategory);
        subCategoryList1.add(subCategory2);
        subCategoryArtsList.add(subCategoryArts);
        subCategoryArtsList.add(subCategoryArts2);
        list.getCategoryList().add(new Category(1, "Antiques & Collectables", subCategoryList1));
        list.getCategoryList().add(new Category(2, "Art & Crafts", subCategoryArtsList));
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
