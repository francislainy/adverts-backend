package com.example.adverts.service.interfaces.category;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryQueryService {

    CategoryQueryDto getCategory(UUID id);

    List<CategoryQueryDto> getAllCategories();

}
