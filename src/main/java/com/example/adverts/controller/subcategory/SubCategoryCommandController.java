package com.example.adverts.controller.subcategory;

import com.example.adverts.model.FeedbackMessage;
import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category/{categoryId}/subCategory")
@RestController
public class SubCategoryCommandController {

    @Autowired
    private SubCategoryCommandService subCategoryCommandService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createSubCategory(@Valid @RequestBody SubCategoryCreateDto subCategoryCreateDto, @PathVariable(value = "categoryId") UUID categoryId) {

        if (categoryRepository.existsById(categoryId)) {
            return new ResponseEntity<>(subCategoryCommandService.createSubCategory(subCategoryCreateDto, categoryId), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new FeedbackMessage("Entity not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{subCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubCategoryUpdateDto> updateSubCategory(@PathVariable(value = "subCategoryId") UUID subCategoryId,
                                                                  @RequestBody SubCategoryUpdateDto subCategoryUpdateDto, @PathVariable(value = "categoryId") UUID id) {
        return new ResponseEntity<>(subCategoryCommandService.updateSubCategory(subCategoryId, subCategoryUpdateDto, id), HttpStatus.OK);
    }

}
