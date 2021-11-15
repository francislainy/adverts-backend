package com.example.adverts.controller;

import com.example.adverts.model.CategoriesOld;
import com.example.adverts.model.CategoryOld;
import com.example.adverts.repository.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CategoryControllerOld {

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping(produces = "application/json")
    public CategoriesOld getCategories() {
        return categoryDao.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryOld category(@PathVariable(value = "id") Long id) {
        return categoryDao.getCategory(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryOld category)
    {
        Integer id = categoryDao.getAllCategories().getCategoryList().size() + 1;
        category.setId(id);

        categoryDao.addCategory(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
