package com.example.adverts.controller.category;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category")
@RestController
public class CategoryQueryController {

    @Autowired
    private CategoryQueryService categoryQueryService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> listAllCategories() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("numProducts", productRepository.count());
        result.put("categories", categoryQueryService.getAllCategories());
        return result;
    }

    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryQueryDto> getCategory(@PathVariable(value = "categoryId") UUID categoryId) {
        return new ResponseEntity<>(categoryQueryService.getCategory(categoryId), HttpStatus.OK);
    }

}
