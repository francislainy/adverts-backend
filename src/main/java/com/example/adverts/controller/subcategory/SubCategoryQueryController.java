package com.example.adverts.controller.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/category/{categoryId}/subCategory")
@RestController
public class SubCategoryQueryController {

    @Autowired
    private SubCategoryQueryService subCategoryQueryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<SubCategoryQueryDto>> listAllSubCategories(@PathVariable(value = "categoryId") UUID categoryId) {

        HashMap<String, List<SubCategoryQueryDto>> result = new HashMap<>();
        result.put("subCategories", subCategoryQueryService.getAllSubCategories(categoryId));
        return result;

    }

    @GetMapping(value = "/{subCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubCategoryQueryDto> getCategory(@PathVariable(value = "categoryId") UUID categoryId, @PathVariable(value = "subCategoryId") UUID subCategoryId) {
        return new ResponseEntity<>(subCategoryQueryService.getSubCategory(subCategoryId, categoryId), HttpStatus.OK);
    }

}
