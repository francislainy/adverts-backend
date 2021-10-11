package com.example.adverts.controller;

import com.example.adverts.entity.CategoryDao;
import com.example.adverts.model.Categories;
import com.example.adverts.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CategoryController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/category")
    public Category greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Category(counter.incrementAndGet(), String.format(template, name));
    }


    @Autowired
    private CategoryDao categoryDao;

    @GetMapping(produces = "application/json")
    public Categories getCategories() {
        return categoryDao.getAllCategories();
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Category category)
    {
        Integer id = categoryDao.getAllCategories().getCategoryList().size() + 1;
        category.setId(id);

        categoryDao.addEmployee(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
