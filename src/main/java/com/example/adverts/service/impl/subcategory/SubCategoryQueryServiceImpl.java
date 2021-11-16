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
    public SubCategoryQueryDto getSubCategory(UUID id) {

        if (subCategoryRepository.findById(id).isPresent()) {
            SubCategory subCategory = subCategoryRepository.findById(id).get(); //todo: should return category 15/11/2021

            return new SubCategoryQueryDto(subCategory.getId(), subCategory.getTitle());

        } else {
            return null;
        }

    }


    @Override
    public List<SubCategoryQueryDto> getAllSubCategories() {
        List<SubCategoryQueryDto> subCategoryList = new ArrayList<>();

        subCategoryRepository.findAll().forEach(subCategory -> {
            subCategoryList.add(new SubCategoryQueryDto(subCategory.getId(), subCategory.getTitle()));
        });

        return subCategoryList;

    }
}


