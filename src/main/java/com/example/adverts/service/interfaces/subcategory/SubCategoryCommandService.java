package com.example.adverts.service.interfaces.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubCategoryCommandService {

    SubCategoryCreateDto createSubCategory(SubCategoryCreateDto subCategoryCreateDto, UUID categoryId);

}
