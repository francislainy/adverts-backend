package com.example.adverts.service.subcategory;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(SubCategoryCommandService.class)
class SubCategoryCommandServiceTest {

    @MockBean
    Category category;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    SubCategoryRepository subCategoryRepository;

    @Autowired
    private SubCategoryCommandService subCategoryCommandService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testSubCategoryItemSavedToDb() {
        Category category = new Category();
        category.setId(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        SubCategory subCategoryMocked = new SubCategory(UUID.fromString("03c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", category, null);

        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(subCategoryMocked);
        when(categoryRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(category));

        SubCategoryCreateDto subCategoryCreateDto = new SubCategoryCreateDto("subCategory", UUID.fromString("03c903f7-7a55-470d-8449-cf7587f5a3fb"));

        subCategoryCreateDto = subCategoryCommandService.createSubCategory(subCategoryCreateDto, UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(subCategoryCreateDto);
        assertEquals(subCategoryMocked.getId(), subCategoryCreateDto.getId());
        assertEquals(subCategoryMocked.getTitle(), subCategoryCreateDto.getTitle());
    }

    @Test
    void testSubCategoryItemUpdated() {
        UUID categoryUuid = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        Category categoryRetrievedMocked = new Category(categoryUuid, "title", null, null);

        when(categoryRepository.findById(categoryUuid))
                .thenReturn(java.util.Optional.of(categoryRetrievedMocked));

        UUID subCategoryUuid = UUID.fromString("03c903f7-7a55-470d-8449-cf7587f5a3fb");
        SubCategory subCategoryRetrievedMocked = new SubCategory(subCategoryUuid, "title", categoryRetrievedMocked, null);
        SubCategory subCategoryUpdatedMocked = new SubCategory(subCategoryUuid, "updated", categoryRetrievedMocked, null);

        when(subCategoryRepository.findById(subCategoryUuid))
                .thenReturn(java.util.Optional.of(subCategoryRetrievedMocked));
        when(subCategoryRepository.save(subCategoryUpdatedMocked)).thenReturn(subCategoryRetrievedMocked);

        SubCategoryUpdateDto subCategoryUpdateDto = new SubCategoryUpdateDto("updated");
        subCategoryUpdateDto = subCategoryCommandService.updateSubCategory(subCategoryUuid, subCategoryUpdateDto, categoryUuid);

        assertNotNull(subCategoryUpdateDto);
        assertEquals(subCategoryUpdatedMocked.getId(), subCategoryUpdateDto.getId());
        assertEquals(subCategoryUpdatedMocked.getTitle(), subCategoryUpdateDto.getTitle());
    }

}
