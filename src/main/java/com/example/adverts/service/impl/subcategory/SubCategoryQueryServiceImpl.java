package com.example.adverts.service.impl.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SubCategoryQueryServiceImpl implements SubCategoryQueryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryQueryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategoryQueryDto getSubCategory(UUID subCategoryId, UUID categoryId) {

        if (subCategoryRepository.findById(subCategoryId).isPresent()) {
            SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get(); //todo: should return category 15/11/2021

            if (subCategory.getCategory().getId().equals(categoryId)) {
                return new SubCategoryQueryDto(subCategory.getId(), subCategory.getTitle(), subCategory.getCategory());
            }
            else {
                return null;
            }

        } else {
            return null;
        }

    }


    @Override
    public List<SubCategoryQueryDto> getAllSubCategories(UUID categoryId) {
        List<SubCategoryQueryDto> subCategoryList = new ArrayList<>();

        subCategoryRepository.findAll().forEach(subCategory -> {

            if (subCategory.getCategory().getId().equals(categoryId)) {
                subCategoryList.add(new SubCategoryQueryDto(subCategory.getId(), subCategory.getTitle(), subCategory.getCategory()));
            }

        });

        return subCategoryList;

    }
}


