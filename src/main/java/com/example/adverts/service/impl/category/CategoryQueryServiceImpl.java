package com.example.adverts.service.impl.category;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryQueryDto getCategory(UUID id) {

        if (categoryRepository.findById(id).isPresent()) {
            Category category = categoryRepository.findById(id).get();

            return new CategoryQueryDto(category.getId(), category.getTitle(), categoryRepository.findAllChildrenCount(id));

        } else {
            return null;
        }

    }


    @Override
    public List<CategoryQueryDto> getAllCategories() {

        List<CategoryQueryDto> categoryList = new ArrayList<>();

        categoryRepository.findAll().forEach(category -> {

            categoryList.add(new CategoryQueryDto(category.getId(), category.getTitle(), categoryRepository.findAllChildrenCount(category.getId())));
        });

        return categoryList;

    }


}


