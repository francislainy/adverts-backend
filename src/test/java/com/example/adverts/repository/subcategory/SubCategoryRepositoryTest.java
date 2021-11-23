package com.example.adverts.repository.subcategory;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubCategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Test
    void findOne() {
        SubCategory subCategory = new SubCategory();
        Category category = new Category();
        category.setId(UUID.randomUUID());
        subCategory.setCategory(category);
        subCategory.setTitle("subCategory");
        subCategory = subCategoryRepository.save(subCategory);
        SubCategory fetchedCategory = subCategoryRepository.findById(subCategory.getId()).get();
        assertNotNull(fetchedCategory);
    }

    @Test
    void findAll() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        List<SubCategory> subCategoryList = List.of(
                new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", category, null)
        );
        subCategoryRepository.saveAll(subCategoryList);
        List<SubCategory> allCategories = (List<SubCategory>) subCategoryRepository.findAll();
        assertTrue(allCategories.size() > 0);
    }

}
