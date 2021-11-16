package com.example.adverts.service.interfaces.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SubCategoryQueryService {

    SubCategoryQueryDto getSubCategory(UUID subCategoryId, UUID categoryId);

    List<SubCategoryQueryDto> getAllSubCategories(UUID categoryId);

}
