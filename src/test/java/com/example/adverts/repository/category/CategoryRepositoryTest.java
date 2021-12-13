package com.example.adverts.repository.category;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

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
                new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category", null, null)
        );
        categoryRepository.saveAll(categoryList);
        List<Category> allCategories = (List<Category>) categoryRepository.findAll();
        assertTrue(allCategories.size() > 0);
    }


    @Test
    void findAllSubCategoryChildren() {

        UUID subCategoryId = UUID.fromString("72c903f7-7a55-470d-8449-cf7587f5a3fb");

        Category category = new Category();
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        category = categoryRepository.save(category);

        subCategoryRepository.save(subCategory);
        categoryRepository.countSubCategories(category.getId());
        Long subCategoriesCount = categoryRepository.countSubCategories(category.getId());
        assertEquals(1, subCategoriesCount);
    }
}
