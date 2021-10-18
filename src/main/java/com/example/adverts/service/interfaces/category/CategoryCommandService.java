package com.example.adverts.service.interfaces.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryCommandService {

    CategoryCreateDto createCategory(CategoryCreateDto categoryCreateDto);

}
