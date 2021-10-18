package com.example.adverts.service.impl.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryCommandImpl implements CategoryCommandService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public CategoryCreateDto createCategory(CategoryCreateDto categoryCreateDto) {

        Category category = new Category();
        category.setTitle(categoryCreateDto.getTitle());

        category = categoryRepository.save(category);

        return new CategoryCreateDto(category.getId(), category.getTitle());
    }


    @Override
    public CategoryUpdateDto updateCategory(UUID id, CategoryUpdateDto categoryUpdateDto) {

        if (categoryRepository.findById(id).isPresent()) {

            Category category = categoryRepository.findById(id).get();

            category.setTitle(categoryUpdateDto.getTitle());

            category = categoryRepository.save(category);

            return new CategoryUpdateDto(category.getId(), category.getTitle());

        } else {
            return null;
        }
    }


}
