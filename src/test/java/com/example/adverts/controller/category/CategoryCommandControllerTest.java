package com.example.adverts.controller.category;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebMvcTest(CategoryCommandController.class)
class CategoryCommandControllerTest {

    Logger logger = LoggerFactory.getLogger(CategoryCommandController.class);

    @MockBean
    private CategoryCommandService categoryCommandService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void testCreateCategory() throws Exception {

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("category");
        CategoryCreateDto categoryCreateResponseDto = new CategoryCreateDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");

        String jsonCreate = asJsonString(categoryCreateDto);
        String jsonResponse = asJsonString(categoryCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryCommandService.createCategory(categoryCreateDto)).thenReturn(
                categoryCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("category"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testCreateCategoryThrowsErrorWhenTitleDoesNotExist() throws Exception {

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        String jsonCreate = asJsonString(categoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryCommandService.createCategory(categoryCreateDto)).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Missing title\"}", true))
                .andReturn();


        logger.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testUpdateCategory() throws Exception {

        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();
        categoryUpdateDto.setTitle("updated category");
        CategoryUpdateDto categoryUpdateResponseDto = new CategoryUpdateDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "updated category");

        String jsonUpdateBody = asJsonString(categoryUpdateDto);
        String jsonResponse = asJsonString(categoryUpdateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/adverts/category/2da4002a-31c5-4cc7-9b92-cbf0db998c41")
                .content(jsonUpdateBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryCommandService.updateCategory(any(UUID.class), eq(categoryUpdateDto))).thenReturn(
                categoryUpdateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("updated category"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
