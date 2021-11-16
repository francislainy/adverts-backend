package com.example.adverts.service.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.impl.subcategory.SubCategoryQueryServiceImpl;
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

@WebMvcTest(SubCategoryQueryService.class)
public class SubCategoryQueryServiceTest {

    @Mock
    SubCategoryRepository subCategoryRepository;

    @MockBean
    private SubCategoryQueryService subCategoryQueryService;

    @BeforeEach
    void initUseCase() {
        subCategoryQueryService = new SubCategoryQueryServiceImpl(subCategoryRepository);
    }

    @Test
    public void testGetSubCategory() {

        SubCategory subCategoryMocked = new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", null, null);

        when(subCategoryRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(subCategoryMocked));

        SubCategoryQueryDto subCategoryQueryDto = subCategoryQueryService.getSubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(subCategoryQueryDto);
        assertEquals(subCategoryMocked.getId(), subCategoryQueryDto.getId());
        assertEquals(subCategoryMocked.getTitle(), subCategoryQueryDto.getTitle());
    }


    @Test
    public void testGetMultipleSubCategory() {

        SubCategory subCategoryMocked1 = new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "sunCategory1", null, null);
        SubCategory subCategoryMocked2 = new SubCategory(UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"), "subCategory2", null, null);

        List<SubCategory> subCategoryMockedList = List.of(subCategoryMocked1, subCategoryMocked2);

        when(subCategoryRepository.findAll()).thenReturn(subCategoryMockedList);

        List<SubCategoryQueryDto> subCategoryQueryDtoList = subCategoryQueryService.getAllSubCategories();

        assertNotNull(subCategoryQueryDtoList);
        assertEquals(subCategoryMockedList.size(), subCategoryQueryDtoList.size());
        assertEquals(subCategoryMockedList.get(0).getId(), subCategoryQueryDtoList.get(0).getId());
        assertEquals(subCategoryMockedList.get(0).getTitle(), subCategoryQueryDtoList.get(0).getTitle());
        assertEquals(subCategoryMockedList.get(1).getId(), subCategoryQueryDtoList.get(1).getId());
        assertEquals(subCategoryMockedList.get(1).getTitle(), subCategoryQueryDtoList.get(1).getTitle());
    }


    @Test
    public void testCreateSubCategory() {

        SubCategory subCategoryMocked = new SubCategory(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", null, null);

        when(subCategoryRepository.save(subCategoryMocked)).thenReturn(subCategoryMocked);

        SubCategory subCategory = subCategoryRepository.save(subCategoryMocked);

        assertNotNull(subCategory);
    }

}
