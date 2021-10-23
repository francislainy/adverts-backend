package com.example.adverts.service;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.impl.category.CategoryQueryServiceImpl;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CategoryQueryService.class)
public class CategoryQueryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @MockBean
    private CategoryQueryService categoryQueryService;

    @InjectMocks
    private CategoryQueryServiceImpl categoryQueryServiceImpl;


    @Test
    public void testCategoryItemFoundOnDb() {
        Category categoryMocked = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category");

        Mockito.when(categoryRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(categoryMocked));

        CategoryQueryDto categoryQueryDto = categoryQueryServiceImpl.getCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(categoryQueryDto);
        assertEquals(categoryMocked.getId(), categoryQueryDto.getId());
        assertEquals(categoryMocked.getTitle(), categoryQueryDto.getTitle());
    }
    

    @Test
    public void testCategoryMultipleItemFoundOnDb() {

        Category categoryMocked1 = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category");
        Category categoryMocked2 = new Category(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "category");

        List<Category> categoryMockedList = List.of(categoryMocked1, categoryMocked2);

        Mockito.when(categoryRepository.findAll()).thenReturn(categoryMockedList);

        List<CategoryQueryDto> categoryQueryDtoList = categoryQueryServiceImpl.getAllCategories();

        assertNotNull(categoryQueryDtoList);
        assertEquals(categoryMockedList.size(), categoryQueryDtoList.size());
        assertEquals(categoryMockedList.get(0).getId(), categoryQueryDtoList.get(0).getId());
        assertEquals(categoryMockedList.get(0).getTitle(), categoryQueryDtoList.get(0).getTitle());
        assertEquals(categoryMockedList.get(1).getId(), categoryQueryDtoList.get(1).getId());
        assertEquals(categoryMockedList.get(1).getTitle(), categoryQueryDtoList.get(0).getTitle());
    }


}
