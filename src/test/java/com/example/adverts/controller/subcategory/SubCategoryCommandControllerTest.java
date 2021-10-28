package com.example.adverts.controller.subcategory;

import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubCategoryCommandController.class)
public class SubCategoryCommandControllerTest {

    @MockBean
    private SubCategoryCommandService subCategoryCommandService;

    @MockBean
    private CategoryCommandService categoryCommandService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSubCategory() throws Exception {

        SubCategoryCreateDto subCategoryCreateDto = new SubCategoryCreateDto("subcategory", UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41"));

        String jsonCreate = asJsonString(subCategoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41/subcategory")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        SubCategoryCreateDto subCategoryCreateResponseDto = new SubCategoryCreateDto(UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41"), "subcategory", UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"));

        when(subCategoryCommandService.createSubCategory(eq(subCategoryCreateDto), any(UUID.class))).thenReturn(
                subCategoryCreateResponseDto);

        String jsonResponse = asJsonString(subCategoryCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value("3fa4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("subcategory"))
                .andExpect(jsonPath("$.categoryId").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
