package com.example.adverts.service;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.impl.category.CategoryQueryServiceImpl;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryQueryService.class)
public class CategoryQueryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @MockBean
    private CategoryQueryService categoryQueryService;

//    @InjectMocks
//    private CategoryQueryServiceImpl categoryQueryServiceImpl;

    @BeforeEach
    void initUseCase() {
        categoryQueryService = new CategoryQueryServiceImpl(categoryRepository);
    }

    @Test
    public void testCategoryItemFoundOnDb() {
        Category categoryMocked = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category", null);

        when(categoryRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(categoryMocked));

        CategoryQueryDto categoryQueryDto = categoryQueryService.getCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb")); // use categoryQueryServiceImpl when autowired instead of controller

        assertNotNull(categoryQueryDto);
        assertEquals(categoryMocked.getId(), categoryQueryDto.getId());
        assertEquals(categoryMocked.getTitle(), categoryQueryDto.getTitle());
    }

    @Test
    public void testCategoryMultipleItemFoundOnDb() {

        Category categoryMocked1 = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category1", null);
        Category categoryMocked2 = new Category(UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"), "category2", null);

        List<Category> categoryMockedList = List.of(categoryMocked1, categoryMocked2);

        when(categoryRepository.findAll()).thenReturn(categoryMockedList);

        List<CategoryQueryDto> categoryQueryDtoList = categoryQueryService.getAllCategories();

        assertNotNull(categoryQueryDtoList);
        assertEquals(categoryMockedList.size(), categoryQueryDtoList.size());
        assertEquals(categoryMockedList.get(0).getId(), categoryQueryDtoList.get(0).getId());
        assertEquals(categoryMockedList.get(0).getTitle(), categoryQueryDtoList.get(0).getTitle());
        assertEquals(categoryMockedList.get(1).getId(), categoryQueryDtoList.get(1).getId());
        assertEquals(categoryMockedList.get(1).getTitle(), categoryQueryDtoList.get(1).getTitle());
    }

    @Test
    public void testCategoryItemSavedToDb() {

        Category categoryMocked = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category", null);

        when(categoryRepository.save(categoryMocked)).thenReturn(categoryMocked);

        Category category = categoryRepository.save(categoryMocked);

        assertNotNull(category);
    }

}
