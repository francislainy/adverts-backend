package com.example.adverts.service.interfaces.subcategory;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SubCategoryQueryService {

    SubCategoryQueryDto getSubCategory(UUID subCategoryId, UUID categoryId);

    List<SubCategoryQueryNoParentDto> getAllSubCategories(UUID categoryId);

    CategoryQueryDto getCategory(UUID categoryId);
}
