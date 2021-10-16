package com.example.adverts.controller;

import com.example.adverts.repository.SubCategoryDao;
import com.example.adverts.model.SubCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubCategoryController {

    @Autowired
    private SubCategoryDao subCategoryDao;

    @GetMapping(value="{id}/subcategory", produces = "application/json")
    public SubCategories getCategories(@PathVariable(value = "id") Long id) {
        return subCategoryDao.getAllSubCategoriesForCategory(id);
    }

//    @GetMapping("subcategory/{id}")
//    public Category category(@PathVariable(value = "id") Long id) {
//        return subCategoryDao.getCategory(id);
//    }
}
