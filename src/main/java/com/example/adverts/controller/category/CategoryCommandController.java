package com.example.adverts.controller.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category")
@RestController
public class CategoryCommandController {

    @Autowired
    private CategoryCommandService categoryCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryCreateDto> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        return new ResponseEntity<>(categoryCommandService.createCategory(categoryCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryUpdateDto> updateCategory(@PathVariable(value = "id") UUID id,
                                                            @RequestBody CategoryUpdateDto categoryUpdateDto) {
        return new ResponseEntity<>(categoryCommandService.updateCategory(id, categoryUpdateDto), HttpStatus.OK);
    }

}
