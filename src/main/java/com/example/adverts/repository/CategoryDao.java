package com.example.adverts.repository;

import com.example.adverts.model.CategoriesOld;
import com.example.adverts.model.CategoryOld;
import com.example.adverts.model.Product;
import com.example.adverts.model.SubCategoryOld;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao
{
    public static final CategoriesOld list = new CategoriesOld();
    public static final ArrayList<SubCategoryOld> subCategoryList1 = new ArrayList<>();
    public static Product product = new Product(1, "prod name", "desc", "http", 10.0, "dublin");
    public static List<Product> productList = new ArrayList<>();

    public static final SubCategoryOld subCategory = new SubCategoryOld(1l,"Autographs", productList);
    public static final SubCategoryOld subCategory2 = new SubCategoryOld(2l,"Bottles", null);


    public static final ArrayList<SubCategoryOld> subCategoryArtsList = new ArrayList<>();
    public static final SubCategoryOld subCategoryArts = new SubCategoryOld(1l,"Art Supplies", null);
    public static final SubCategoryOld subCategoryArts2 = new SubCategoryOld(2l,"Artisan Food", null);

    static
    {
        productList.add(product);
        subCategoryList1.add(subCategory);
        subCategoryList1.add(subCategory2);
        subCategoryArtsList.add(subCategoryArts);
        subCategoryArtsList.add(subCategoryArts2);
        list.getCategoryList().add(new CategoryOld(1, "Antiques & Collectables", subCategoryList1));
        list.getCategoryList().add(new CategoryOld(2, "Art & Crafts", subCategoryArtsList));
        list.getCategoryList().add(new CategoryOld(3, "Baby & Nursery", null));
    }

    public CategoriesOld getAllCategories()
    {
        return list;
    }

    public CategoryOld getCategory(Long id) {

        for (CategoryOld c: list.getCategoryList()) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public void addCategory(CategoryOld category) {
        list.getCategoryList().add(category);
    }
}
