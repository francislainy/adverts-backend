package com.example.adverts.service.impl.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
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

        Category newCategory = new Category();
//        newCategory.setId(UUID.randomUUID());
        newCategory.setTitle(categoryCreateDto.getTitle());

        newCategory = categoryRepository.save(newCategory);

        return new CategoryCreateDto(newCategory.getId(), newCategory.getTitle());

    }

}
