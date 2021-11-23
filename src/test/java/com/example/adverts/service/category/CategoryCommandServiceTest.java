package com.example.adverts.service.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.impl.category.CategoryCommandImpl;
import com.example.adverts.service.impl.category.CategoryQueryServiceImpl;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryCommandService.class)
class CategoryCommandServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @MockBean
    private CategoryQueryService categoryQueryService;

    @MockBean
    private CategoryCommandService categoryCommandService;

    @BeforeEach
    void initUseCase() {
        categoryQueryService = new CategoryQueryServiceImpl(categoryRepository);
        categoryCommandService = new CategoryCommandImpl(categoryRepository);
    }

    @Test
    void testCategoryItemSavedToDb() {

        Category categoryMocked = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category", null, null);

        when(categoryRepository.save(any(Category.class))).thenReturn(categoryMocked);

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category");

        categoryCreateDto = categoryCommandService.createCategory(categoryCreateDto);

        assertNotNull(categoryCreateDto);
        assertEquals(categoryMocked.getId(), categoryCreateDto.getId());
        assertEquals(categoryMocked.getTitle(), categoryCreateDto.getTitle());
    }


    @Test
    void testCategoryItemUpdated() {
        UUID categoryUuid = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        Category categoryRetrievedMocked = new Category(categoryUuid, "title", null, null);
        Category categoryUpdatedMocked = new Category(categoryUuid, "updated", null, null);

        when(categoryRepository.findById(categoryUuid))
                .thenReturn(java.util.Optional.of(categoryRetrievedMocked));
        when(categoryRepository.save(categoryUpdatedMocked)).thenReturn(categoryUpdatedMocked);

        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto( "updated");
        categoryUpdateDto = categoryCommandService.updateCategory(categoryUuid, categoryUpdateDto);

        assertNotNull(categoryUpdateDto);
        assertEquals(categoryUpdatedMocked.getId(), categoryUpdateDto.getId());
        assertEquals(categoryUpdatedMocked.getTitle(), categoryUpdateDto.getTitle());
    }

}
