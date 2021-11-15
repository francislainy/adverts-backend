package com.example.adverts.controller.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubCategoryQueryController.class)
public class SubCategoryQueryControllerTest {

    @MockBean
    private SubCategoryQueryService subCategoryQueryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllSubCategoriesWhenOneItemOnly() throws Exception {

        SubCategoryQueryDto SubCategoryQueryDto = new SubCategoryQueryDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "subCategory");
        List<SubCategoryQueryDto> subCategoryQueryDtoList = List.of(SubCategoryQueryDto);

        //todo: this should have an id for the category -
        Mockito.when(subCategoryQueryService.getAllSubCategories()).thenReturn(
                subCategoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41/subcategory")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<SubCategoryQueryDto>> result = new HashMap<>();
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.subCategories.size()").value(1))
                .andExpect(jsonPath("$.subCategories[0].id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testGetAllCategoriesWhenTwoItems() throws Exception {

        SubCategoryQueryDto subCategoryQueryDto1 = new SubCategoryQueryDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "subCategory1");
        SubCategoryQueryDto subCategoryQueryDto2 = new SubCategoryQueryDto(UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"), "subCategory2");
        List<SubCategoryQueryDto> subCategoryQueryDtoList = List.of(subCategoryQueryDto1, subCategoryQueryDto2);

        Mockito.when(subCategoryQueryService.getAllSubCategories()).thenReturn(
                subCategoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41/subcategory")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<SubCategoryQueryDto>> result = new HashMap<>();
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.subCategories.size()").value(2))
                .andExpect(jsonPath("$.subCategories[0].id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory1"))
                .andExpect(jsonPath("$.subCategories[1].id").value("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"))
                .andExpect(jsonPath("$.subCategories[1].title").value("subCategory2"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testGetCategoryItem() throws Exception {

        SubCategoryQueryDto subCategoryQueryDto = new SubCategoryQueryDto(UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");

        Mockito.when(subCategoryQueryService.getSubCategory(UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41"))).thenReturn(
                subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41/subcategory/3ba4002a-31c5-4cc7-9b92-cbf0db998c41")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        String json = asJsonString(subCategoryQueryDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value("3ba4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
