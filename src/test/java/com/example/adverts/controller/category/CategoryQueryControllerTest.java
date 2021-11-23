package com.example.adverts.controller.category;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryQueryController.class)
class CategoryQueryControllerTest {

    @MockBean
    private CategoryQueryService categoryQueryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllCategoriesWhenOneItemOnly() throws Exception {

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");
        List<CategoryQueryDto> categoryQueryDtoList = List.of(categoryQueryDto);

        Mockito.when(categoryQueryService.getAllCategories()).thenReturn(
                categoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<CategoryQueryDto>> result = new HashMap<>();
        result.put("categories", categoryQueryDtoList);

        String json = asJsonString(result);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.categories.size()").value(1))
                .andExpect(jsonPath("$.categories[0].id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.categories[0].title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetAllCategoriesWhenTwoItems() throws Exception {

        CategoryQueryDto categoryQueryDto1 = new CategoryQueryDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category1");
        CategoryQueryDto categoryQueryDto2 = new CategoryQueryDto(UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"), "category2");
        List<CategoryQueryDto> categoryQueryDtoList = List.of(categoryQueryDto1, categoryQueryDto2);

        Mockito.when(categoryQueryService.getAllCategories()).thenReturn(
                categoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<CategoryQueryDto>> result = new HashMap<>();
        result.put("categories", categoryQueryDtoList);

        String json = asJsonString(result);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.categories.size()").value(2))
                .andExpect(jsonPath("$.categories[0].id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.categories[0].title").value("category1"))
                .andExpect(jsonPath("$.categories[1].id").value("7bc5102a-31c5-1cc7-9b92-cbf0db865c89"))
                .andExpect(jsonPath("$.categories[1].title").value("category2"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetCategoryItem() throws Exception {

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");

        Mockito.when(categoryQueryService.getCategory(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))).thenReturn(
                categoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        String json = asJsonString(categoryQueryDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
