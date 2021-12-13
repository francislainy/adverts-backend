package com.example.adverts.controller.product;

import com.example.adverts.model.FeedbackMessage;
import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product")
@RestController
public class ProductCommandController {

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createProduct(@RequestBody ProductCreateDto productCreateDto, @PathVariable UUID categoryId, @PathVariable UUID subCategoryId) {
        if (categoryRepository.existsById(categoryId) && subCategoryRepository.existsById(subCategoryId)) {

            if (productCreateDto.getTitle() != null && productCreateDto.getPrice() != null && productCreateDto.getDescription() != null) {
                return new ResponseEntity<>(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new FeedbackMessage("Missing mandatory field"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new FeedbackMessage("Entity not found"), HttpStatus.BAD_REQUEST);
        }
    }

}
