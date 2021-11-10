package com.example.adverts.controller.product;

import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductCreateDto> createProduct(@RequestBody ProductCreateDto productCreateDto, @PathVariable UUID categoryId, @PathVariable UUID subCategoryId) {
        return new ResponseEntity<>(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId), HttpStatus.CREATED);
    }

}
