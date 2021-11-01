package com.example.adverts.service.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.impl.category.CategoryQueryServiceImpl;
import com.example.adverts.service.impl.subcategory.SubCategoryQueryServiceImpl;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryQueryService.class)
public class SubCategoryQueryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    SubCategoryRepository subCategoryRepository;

    @MockBean
    private SubCategoryQueryService subCategoryQueryService;

    @MockBean
    private CategoryQueryService categoryQueryService;


    @BeforeEach
    void initUseCase() {

        subCategoryQueryService = new SubCategoryQueryServiceImpl(subCategoryRepository);
        categoryQueryService = new CategoryQueryServiceImpl(categoryRepository);
    }

    @Test
    public void testSubCategoryItemFoundOnDb() {
        SubCategory subCategoryMocked = new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", null);

        when(subCategoryRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(subCategoryMocked));

        SubCategoryQueryDto subCategoryQueryDto = subCategoryQueryService.getSubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(subCategoryQueryDto);
        assertEquals(subCategoryMocked.getId(), subCategoryQueryDto.getId());
        assertEquals(subCategoryMocked.getTitle(), subCategoryQueryDto.getTitle());
    }


    @Test
    public void testSubCategoryMultipleItemFoundOnDb() {

        SubCategory subCategoryMocked1 = new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "sunCategory1", null);
        SubCategory subCategoryMocked2 = new SubCategory(UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"), "subCategory2", null);

        List<SubCategory> subCategoryMockedList = List.of(subCategoryMocked1, subCategoryMocked2);

        when(subCategoryRepository.findAll()).thenReturn(subCategoryMockedList);

        List<SubCategoryQueryDto> categoryQueryDtoList = subCategoryQueryService.getAllSubCategories();

        assertNotNull(categoryQueryDtoList);
        assertEquals(subCategoryMockedList.size(), categoryQueryDtoList.size());
        assertEquals(subCategoryMockedList.get(0).getId(), categoryQueryDtoList.get(0).getId());
        assertEquals(subCategoryMockedList.get(0).getTitle(), categoryQueryDtoList.get(0).getTitle());
        assertEquals(subCategoryMockedList.get(1).getId(), categoryQueryDtoList.get(1).getId());
        assertEquals(subCategoryMockedList.get(1).getTitle(), categoryQueryDtoList.get(1).getTitle());
    }


    @Test
    public void testCategoryItemSavedToDb() {

        SubCategory subCategoryMocked = new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", null);

        when(subCategoryRepository.save(subCategoryMocked)).thenReturn(subCategoryMocked);

        SubCategory category = subCategoryRepository.save(subCategoryMocked);

        assertNotNull(category);
    }

}
