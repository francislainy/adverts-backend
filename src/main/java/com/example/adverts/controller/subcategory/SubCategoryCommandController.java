package com.example.adverts.controller.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category/{id}/subcategory")
@RestController
public class SubCategoryCommandController {

    @Autowired
    private SubCategoryCommandService subCategoryCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SubCategoryCreateDto> createSubCategory(@RequestBody SubCategoryCreateDto subCategoryCreateDto, @PathVariable UUID id) {
        return new ResponseEntity<>(subCategoryCommandService.createSubCategory(subCategoryCreateDto, id), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{subCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubCategoryUpdateDto> updateSubCategory(@PathVariable(value = "subCategoryId") UUID subCategoryId,
                                                                  @RequestBody SubCategoryUpdateDto subCategoryUpdateDto, @PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(subCategoryCommandService.updateSubCategory(subCategoryId, subCategoryUpdateDto, id), HttpStatus.OK);
    }

}
