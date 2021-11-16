package com.example.adverts.controller.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubCategoryQueryController.class)
public class SubCategoryQueryControllerTest {

    @MockBean
    private SubCategoryQueryService subCategoryQueryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllSubCategoriesWhenOneItemOnly() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryDto subCategoryQueryDto = new SubCategoryQueryDto(subCategoryId, "subCategory", category);
        List<SubCategoryQueryDto> subCategoryQueryDtoList = List.of(subCategoryQueryDto);

        //todo: this should have an id for the category -
        Mockito.when(subCategoryQueryService.getAllSubCategories(categoryId)).thenReturn(
                subCategoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<SubCategoryQueryDto>> result = new HashMap<>();
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.subCategories.size()").value(1))
                .andExpect(jsonPath("$.subCategories[0].id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory"))
                .andExpect(jsonPath("$.subCategories[0].category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.subCategories[0].category.title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testGetAllCategoriesWhenTwoItems() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId1 = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryDto subCategoryQueryDto1 = new SubCategoryQueryDto(subCategoryId1, "subCategory1", category);
        SubCategoryQueryDto subCategoryQueryDto2 = new SubCategoryQueryDto(subCategoryId2, "subCategory2", category);
        List<SubCategoryQueryDto> subCategoryQueryDtoList = List.of(subCategoryQueryDto1, subCategoryQueryDto2);

        Mockito.when(subCategoryQueryService.getAllSubCategories(categoryId)).thenReturn(
                subCategoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<SubCategoryQueryDto>> result = new HashMap<>();
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.subCategories.size()").value(2))
                .andExpect(jsonPath("$.subCategories[0].id").value(subCategoryId1.toString()))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory1"))
                .andExpect(jsonPath("$.subCategories[0].category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.subCategories[0].category.title").value("category"))
                .andExpect(jsonPath("$.subCategories[1].id").value(subCategoryId2.toString()))
                .andExpect(jsonPath("$.subCategories[1].title").value("subCategory2"))
                .andExpect(jsonPath("$.subCategories[1].category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.subCategories[1].category.title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testGetSubCategoryItem() throws Exception {

        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");
        UUID subCategoryId = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryDto subCategoryQueryDto = new SubCategoryQueryDto(subCategoryId, "subCategory", category);

        when(subCategoryQueryService.getSubCategory(subCategoryId, categoryId)).thenReturn(subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}", categoryId, subCategoryId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        String json = asJsonString(subCategoryQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.title").value("subCategory"))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
