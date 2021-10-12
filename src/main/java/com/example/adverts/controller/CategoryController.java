package com.example.adverts.controller;

import com.example.adverts.entity.CategoryDao;
import com.example.adverts.model.Categories;
import com.example.adverts.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping(produces = "application/json")
    public Categories getCategories() {
        return categoryDao.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category category(@PathVariable(value = "id") Long id) {
        return categoryDao.getCategory(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addCategory(@RequestBody Category category)
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
