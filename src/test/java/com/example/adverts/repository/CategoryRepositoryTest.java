package com.example.adverts.repository;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findOne() {
        Category category = new Category();
        category.setTitle("category");
        category = categoryRepository.save(category);
        Category fetchedCategory = categoryRepository.findById(category.getId()).get();
        assertNotNull(fetchedCategory);
    }


    @Test
    void findAll() {
        List<Category> categoryList = List.of(
                new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category")
        );
        categoryRepository.saveAll(categoryList);
        List<Category> allCategories = (List<Category>) categoryRepository.findAll();
        assertTrue(allCategories.size() > 0);
    }
}
