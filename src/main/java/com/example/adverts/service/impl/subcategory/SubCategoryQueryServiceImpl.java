package com.example.adverts.service.impl.subcategory;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubCategoryQueryServiceImpl implements SubCategoryQueryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryQueryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategoryQueryDto getSubCategory(UUID subCategoryId, UUID categoryId) {

        Optional<SubCategory> optional = subCategoryRepository.findById(subCategoryId);
        if (optional.isPresent()) {
            SubCategory subCategory = optional.get(); //todo: should return category 15/11/2021
            if (subCategory.getCategory().getId().equals(categoryId)) {
                return new SubCategoryQueryDto(subCategory.getId(), subCategory.getTitle(), subCategoryRepository.countProducts(subCategory.getId()), subCategory.getCategory());
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public List<SubCategoryQueryDto> getAllSubCategories() {
        List<SubCategoryQueryDto> subCategoryList = new ArrayList<>();

        subCategoryRepository.findByOrderByTitle().forEach(subCategory ->
                subCategoryList.add(new SubCategoryQueryDto(subCategory.getId(), subCategory.getTitle(), subCategoryRepository.countProducts(subCategory.getId()), subCategory.getCategory())));

        return subCategoryList;
    }

    @Override
    public List<SubCategoryQueryNoParentDto> getAllSubCategories(UUID categoryId) {
        List<SubCategoryQueryNoParentDto> subCategoryList = new ArrayList<>();

        subCategoryRepository.findByOrderByTitle().forEach(subCategory -> {

            if (subCategory.getCategory().getId().equals(categoryId)) {
                subCategoryList.add(new SubCategoryQueryNoParentDto(subCategory.getId(), subCategory.getTitle(), subCategoryRepository.countProducts(subCategory.getId())));
            }

        });

        return subCategoryList;
    }

    @Override
    public CategoryQueryDto getCategory(UUID categoryId) {

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto();
        for (SubCategory subCategory : subCategoryRepository.findAll()) {
            if (subCategory.getCategory().getId().equals(categoryId)) {
                categoryQueryDto = CategoryQueryDto.builder()
                        .id(subCategory.getCategory().getId())
                        .title(subCategory.getCategory().getTitle())
                        .build();
                break;
            }
        }

        return categoryQueryDto;
    }

}


