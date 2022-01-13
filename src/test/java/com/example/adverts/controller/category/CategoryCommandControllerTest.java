package com.example.adverts.controller.category;

import jwt.JwtUtil;
import jwt.UserDetailsServiceImpl;
import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.category.CategoryUpdateDto;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentScan({"jwt"})
@WebMvcTest(value = CategoryCommandController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
//@AutoConfigureMockMvc(addFilters = false)
class CategoryCommandControllerTest {

    Logger logger = LoggerFactory.getLogger(CategoryCommandController.class);

    @MockBean
    private CategoryCommandService categoryCommandService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

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
    void testCreateCategoryThrows403WhenNoAuthHeader() throws Exception {

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("category");
        CategoryCreateDto categoryCreateResponseDto = new CategoryCreateDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");

        String jsonCreate = asJsonString(categoryCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryCommandService.createCategory(categoryCreateDto)).thenReturn(
                categoryCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateCategory() throws Exception {

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("category");
        CategoryCreateDto categoryCreateResponseDto = new CategoryCreateDto(UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41"), "category");

        String jsonCreate = asJsonString(categoryCreateDto);
        String jsonResponse = asJsonString(categoryCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        // Mock Service method used in JwtRequestFilter
        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        // Should be createCategory(eq(categoryCreateDto))?
        when(categoryCommandService.createCategory(categoryCreateDto)).thenReturn(
                categoryCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value("2da4002a-31c5-4cc7-9b92-cbf0db998c41"))
                .andExpect(jsonPath("$.title").value("category"))
                .andReturn();

//        String actualJson = mvcResult.getResponse().getContentAsString();
//
//        assertEquals(jsonResponse, actualJson);

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateCategoryThrowsErrorWhenTitleDoesNotExist() throws Exception {

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        String jsonCreate = asJsonString(categoryCreateDto);

        dummy = new User("foo@email.com", "foo", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryCommandService.createCategory(categoryCreateDto)).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]").value(is("Title cannot be empty")))
                .andReturn();


        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateCategoryThrows403WhenNoAuthHeader() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();
        categoryUpdateDto.setTitle("updated category");
        CategoryUpdateDto categoryUpdateResponseDto = new CategoryUpdateDto(categoryId, "updated category");

        String jsonUpdateBody = asJsonString(categoryUpdateDto);
        String jsonResponse = asJsonString(categoryUpdateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/adverts/category/{categoryId}", categoryId)
                .content(jsonUpdateBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryCommandService.updateCategory(any(UUID.class), eq(categoryUpdateDto))).thenReturn(
                categoryUpdateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateCategory() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();
        categoryUpdateDto.setTitle("updated category");
        CategoryUpdateDto categoryUpdateResponseDto = new CategoryUpdateDto(categoryId, "updated category");

        String jsonUpdateBody = asJsonString(categoryUpdateDto);
        String jsonResponse = asJsonString(categoryUpdateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/adverts/category/{categoryId}", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonUpdateBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryCommandService.updateCategory(any(UUID.class), eq(categoryUpdateDto))).thenReturn(
                categoryUpdateResponseDto);


        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.title").value("updated category"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
