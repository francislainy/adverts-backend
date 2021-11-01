package com.example.adverts.service.impl.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubCategoryCommandImpl implements SubCategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryCommandImpl(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategoryCreateDto createSubCategory(SubCategoryCreateDto subCategoryCreateDto, UUID categoryId) {

        if (categoryRepository.findById(categoryId).isPresent()) {

            SubCategory subCategory = new SubCategory();
            subCategory.setCategory(categoryRepository.findById(categoryId).get());
            subCategory.setTitle(subCategoryCreateDto.getTitle());

            subCategory = subCategoryRepository.save(subCategory);

            return new SubCategoryCreateDto(subCategory.getId(), subCategory.getTitle(), subCategory.getCategory().getId());
        } else {
            return null;
        }
    }


    @Override
    public SubCategoryUpdateDto updateSubCategory(UUID subCategoryId, SubCategoryUpdateDto subCategoryUpdateDto, UUID categoryId) {

        if (categoryRepository.findById(categoryId).isPresent() && subCategoryRepository.findById(subCategoryId).isPresent()) {

            SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get();

            subCategory.setTitle(subCategoryUpdateDto.getTitle());

            subCategory = subCategoryRepository.save(subCategory);

            return new SubCategoryUpdateDto(subCategory.getId(), subCategory.getTitle());

        } else {
            return null;
        }
    }
}
