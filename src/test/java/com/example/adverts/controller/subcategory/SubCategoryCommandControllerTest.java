package com.example.adverts.controller.subcategory;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = SubCategoryCommandController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class SubCategoryCommandControllerTest {

    Logger logger = LoggerFactory.getLogger(SubCategoryCommandController.class);

    @MockBean
    private SubCategoryCommandService subCategoryCommandService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new User("foo@email.com", "foo", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    void testCreateSubCategoryThrows403WhenNoAuthHeader() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41");
        SubCategoryCreateDto subCategoryCreateDto = new SubCategoryCreateDto("subCategory", subCategoryId);

        String jsonCreate = asJsonString(subCategoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        SubCategoryCreateDto subCategoryCreateResponseDto = new SubCategoryCreateDto(subCategoryId, "subCategory", categoryId);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryCommandService.createSubCategory(eq(subCategoryCreateDto), any(UUID.class))).thenReturn(
                subCategoryCreateResponseDto);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateSubCategory() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41");
        SubCategoryCreateDto subCategoryCreateDto = new SubCategoryCreateDto("subCategory", subCategoryId);

        String jsonCreate = asJsonString(subCategoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        SubCategoryCreateDto subCategoryCreateResponseDto = new SubCategoryCreateDto(subCategoryId, "subCategory", categoryId);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryCommandService.createSubCategory(eq(subCategoryCreateDto), any(UUID.class))).thenReturn(
                subCategoryCreateResponseDto);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);

        String jsonResponse = asJsonString(subCategoryCreateResponseDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.title").value("subCategory"))
                .andExpect(jsonPath("$.categoryId").value(categoryId.toString()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateSubCategoryThrowsErrorWhenCategoryDoesNotExist() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41");

        SubCategoryCreateDto subCategoryCreateDto = new SubCategoryCreateDto("subCategory", subCategoryId);
        String jsonCreate = asJsonString(subCategoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        SubCategoryCreateDto subCategoryCreateResponseDto = new SubCategoryCreateDto(subCategoryId, "subCategory", categoryId);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryCommandService.createSubCategory(eq(subCategoryCreateDto), any(UUID.class))).thenReturn(
                subCategoryCreateResponseDto);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Entity not found\"}", true))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateSubCategoryThrowsErrorWhenTitleDoesNotExist() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41");

        SubCategoryCreateDto subCategoryCreateDto = new SubCategoryCreateDto(null, subCategoryId);
        String jsonCreate = asJsonString(subCategoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        SubCategoryCreateDto subCategoryCreateResponseDto = new SubCategoryCreateDto(subCategoryId, "subCategory", categoryId);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryCommandService.createSubCategory(eq(subCategoryCreateDto), any(UUID.class))).thenReturn(
                subCategoryCreateResponseDto);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Missing title\"}", true))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateSubCategoryThrows403WhenNoAuthHeader() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41");

        SubCategoryUpdateDto subCategoryUpdateDto = new SubCategoryUpdateDto();
        subCategoryUpdateDto.setTitle("updated subCategory");
        SubCategoryUpdateDto subCategoryUpdateResponseDto = new SubCategoryUpdateDto(subCategoryId, "updated subCategory");

        String jsonUpdateBody = asJsonString(subCategoryUpdateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}", categoryId, subCategoryId)
                .content(jsonUpdateBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryCommandService.updateSubCategory(any(UUID.class), eq(subCategoryUpdateDto), any(UUID.class))).thenReturn(
                subCategoryUpdateResponseDto);

        String jsonResponse = asJsonString(subCategoryUpdateResponseDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateSubCategory() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3fa4002a-31c5-4cc7-9b92-cbf0db998c41");

        SubCategoryUpdateDto subCategoryUpdateDto = new SubCategoryUpdateDto();
        subCategoryUpdateDto.setTitle("updated subCategory");
        SubCategoryUpdateDto subCategoryUpdateResponseDto = new SubCategoryUpdateDto(subCategoryId, "updated subCategory");

        String jsonUpdateBody = asJsonString(subCategoryUpdateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonUpdateBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryCommandService.updateSubCategory(any(UUID.class), eq(subCategoryUpdateDto), any(UUID.class))).thenReturn(
                subCategoryUpdateResponseDto);

        String jsonResponse = asJsonString(subCategoryUpdateResponseDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.title").value("updated subCategory"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
