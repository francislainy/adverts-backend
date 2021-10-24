package com.example.adverts.controller;

import com.example.adverts.controller.category.CategoryCommandController;
import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryCommandController.class)
public class CategoryCommandControllerTest {

    @MockBean
    private CategoryCommandService categoryCommandService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateCategory() throws Exception {

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("category");
        CategoryCreateDto categoryCreateResponseDto = new CategoryCreateDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonResponse = ow.writeValueAsString(categoryCreateResponseDto);
        String jsonCreate = ow.writeValueAsString(categoryCreateDto);

        Mockito.when(categoryCommandService.createCategory(categoryCreateDto)).thenReturn(
                categoryCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testUpdateCategory() throws Exception {

        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();
        categoryUpdateDto.setTitle("updated category");
        CategoryUpdateDto categoryUpdateResponseDto = new CategoryUpdateDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "updated category");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonUpdateBody = ow.writeValueAsString(categoryUpdateDto);
        String jsonResponse = ow.writeValueAsString(categoryUpdateResponseDto);

        Mockito.when(categoryCommandService.updateCategory(any(UUID.class), eq(categoryUpdateDto))).thenReturn(
                categoryUpdateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41")
                .content(jsonUpdateBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("updated category"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
