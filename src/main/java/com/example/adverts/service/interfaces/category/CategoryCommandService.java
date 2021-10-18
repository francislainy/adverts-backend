package com.example.adverts.service.interfaces.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryCommandService {

    CategoryCreateDto createCategory(CategoryCreateDto categoryCreateDto);

    CategoryUpdateDto updateCategory(UUID id, CategoryUpdateDto categoryUpdateDto);

}
