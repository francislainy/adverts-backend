package com.example.adverts.controller.product;

import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.service.interfaces.product.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product")
@RestController
public class ProductQueryController {

    @Autowired
    private ProductQueryService productQueryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listAllProducts(@PathVariable(value = "categoryId") UUID categoryId, @PathVariable(value = "subCategoryId") UUID subCategoryId) {

        HashMap<String, Object> result = new HashMap<>();
        result.put("category", productQueryService.getCategory(categoryId));
        result.put("subCategory", productQueryService.getSubCategory(categoryId));
        result.put("products", productQueryService.getAllProducts(categoryId, subCategoryId));
        return result;
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductQueryDto> getProduct(@PathVariable(value = "productId") UUID productId) {
        return new ResponseEntity<>(productQueryService.getProduct(productId), HttpStatus.OK);
    }

}
