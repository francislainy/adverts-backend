package com.example.adverts.controller.category;

import com.example.adverts.model.FeedbackMessage;
import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.repository.category.CategoryRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {

        if (categoryCreateDto.getTitle() != null) {
            return new ResponseEntity<>(categoryCommandService.createCategory(categoryCreateDto), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(new FeedbackMessage("Missing title"), HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryUpdateDto> updateCategory(@PathVariable(value = "id") UUID id,
                                                            @RequestBody CategoryUpdateDto categoryUpdateDto) {
        return new ResponseEntity<>(categoryCommandService.updateCategory(id, categoryUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedbackMessage> deleteCategory(@PathVariable(value = "id") UUID id) {
        if (categoryRepository.findById(id).isPresent()) {
            if (categoryCommandService.deleteCategory(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(new FeedbackMessage("Item not deleted"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new FeedbackMessage("Item not found"), HttpStatus.NOT_FOUND);
        }
    }

}
