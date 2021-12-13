package com.example.adverts.service.category;

import jwt.JwtUtil;
import jwt.UserDetailsServiceImpl;
import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.impl.category.CategoryQueryServiceImpl;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryQueryService.class)
class CategoryQueryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @MockBean
    private CategoryQueryService categoryQueryService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void initUseCase() {
        categoryQueryService = new CategoryQueryServiceImpl(categoryRepository);
    }

    @Test
    void testGetCategory() {

        UUID categoryId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        Category categoryMocked = new Category(categoryId, "category", null, null);

        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(categoryMocked));
        when(categoryRepository.findAllChildrenCount(any(UUID.class))).thenReturn(2L);

        CategoryQueryDto categoryQueryDto = categoryQueryService.getCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb")); // use categoryQueryServiceImpl when autowired instead of controller

        assertNotNull(categoryQueryDto);
        assertEquals(categoryMocked.getId(), categoryQueryDto.getId());
        assertEquals(categoryMocked.getTitle(), categoryQueryDto.getTitle());
        assertEquals(2L, categoryQueryDto.getCountSubCategories());
    }

    @Test
    void testGetMultipleCategories() {

        UUID categoryId1 = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UUID categoryId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        Category categoryMocked1 = new Category(categoryId1, "category1", null, null);
        Category categoryMocked2 = new Category(categoryId2, "category2", null, null);

        List<Category> categoryMockedList = List.of(categoryMocked1, categoryMocked2);

        when(categoryRepository.findAll()).thenReturn(categoryMockedList);
        when(categoryRepository.findAllChildrenCount(categoryId1)).thenReturn(2L);
        when(categoryRepository.findAllChildrenCount(categoryId2)).thenReturn(1L);

        List<CategoryQueryDto> categoryQueryDtoList = categoryQueryService.getAllCategories();

        assertNotNull(categoryQueryDtoList);
        assertEquals(categoryMockedList.size(), categoryQueryDtoList.size());
        assertEquals(categoryMockedList.get(0).getId(), categoryQueryDtoList.get(0).getId());
        assertEquals(categoryMockedList.get(0).getTitle(), categoryQueryDtoList.get(0).getTitle());
        assertEquals(2L, categoryQueryDtoList.get(0).getCountSubCategories());
        assertEquals(categoryMockedList.get(1).getId(), categoryQueryDtoList.get(1).getId());
        assertEquals(categoryMockedList.get(1).getTitle(), categoryQueryDtoList.get(1).getTitle());
        assertEquals(1L, categoryQueryDtoList.get(1).getCountSubCategories());
    }

}
