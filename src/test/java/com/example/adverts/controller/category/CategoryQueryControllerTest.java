package com.example.adverts.controller.category;

import com.example.adverts.repository.product.ProductRepository;
import jwt.JwtUtil;
import jwt.UserDetailsServiceImpl;
import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.service.interfaces.category.CategoryQueryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ComponentScan({"jwt"})
@WebMvcTest(value = CategoryQueryController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class CategoryQueryControllerTest {

    Logger logger = LoggerFactory.getLogger(CategoryQueryController.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private CategoryQueryService categoryQueryService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new User("foo@email.com", "foo", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    void testGetAllCategoriesThrows403WhenNoAuthHeader() throws Exception {

        UUID categoryId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");

        CategoryQueryDto categoryQueryDto = CategoryQueryDto.builder().id(categoryId).build();

        List<CategoryQueryDto> categoryQueryDtoList = List.of(categoryQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername("foo@email.com")).thenReturn(dummy);
        when(categoryQueryService.getAllCategories()).thenReturn(categoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllCategoriesWhenOneItemOnly() throws Exception {

        UUID categoryId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");

        CategoryQueryDto categoryQueryDto = CategoryQueryDto.builder()
                .id(categoryId)
                .title("category")
                .countSubCategories(1L)
                .countProducts(10L)
                .build();
        List<CategoryQueryDto> categoryQueryDtoList = List.of(categoryQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername("foo@email.com")).thenReturn(dummy);
        when(categoryQueryService.getAllCategories()).thenReturn(categoryQueryDtoList);
        when(productRepository.count()).thenReturn(1L);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("numProducts", 1);
        result.put("categories", categoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.numProducts").value(1))
                .andExpect(jsonPath("$.categories.size()").value(1))
                .andExpect(jsonPath("$.categories[0].id").value(categoryId.toString()))
                .andExpect(jsonPath("$.categories[0].title").value(categoryQueryDto.getTitle()))
                .andExpect(jsonPath("$.categories[0].countSubCategories").value(1))
                .andExpect(jsonPath("$.categories[0].countProducts").value(10))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllCategoriesWhenTwoItems() throws Exception {

        UUID categoryId1 = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UUID categoryId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        CategoryQueryDto categoryQueryDto1 = CategoryQueryDto.builder()
                .id(categoryId1)
                .title("category1")
                .countSubCategories(2L)
                .countProducts(10L)
                .build();
        CategoryQueryDto categoryQueryDto2 = CategoryQueryDto.builder()
                .id(categoryId2)
                .title("category2")
                .countSubCategories(1L)
                .countProducts(100L)
                .build();
        List<CategoryQueryDto> categoryQueryDtoList = List.of(categoryQueryDto1, categoryQueryDto2);

        when(userDetailsServiceImpl.loadUserByUsername("foo@email.com")).thenReturn(dummy);
        when(categoryQueryService.getAllCategories()).thenReturn(categoryQueryDtoList);
        when(productRepository.count()).thenReturn(2L);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("numProducts", 2);
        result.put("categories", categoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.categories.size()").value(2))
                .andExpect(jsonPath("$.categories[0].id").value(categoryId1.toString()))
                .andExpect(jsonPath("$.categories[0].title").value("category1"))
                .andExpect(jsonPath("$.categories[0].countSubCategories").value(2))
                .andExpect(jsonPath("$.categories[0].countProducts").value(10))
                .andExpect(jsonPath("$.categories[1].id").value(categoryId2.toString()))
                .andExpect(jsonPath("$.categories[1].title").value("category2"))
                .andExpect(jsonPath("$.categories[1].countSubCategories").value(1))
                .andExpect(jsonPath("$.categories[1].countProducts").value(100))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetCategoryItemThrows403WhenNoAuthHeader() throws Exception {

        UUID categoryId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(categoryId, "category", 1L, 10L);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}", categoryId)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetCategoryItem() throws Exception {

        UUID categoryId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(categoryId, "category", 1L, 10L);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        String json = asJsonString(categoryQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.title").value("category"))
                .andExpect(jsonPath("$.countSubCategories").value(1))
                .andExpect(jsonPath("$.countProducts").value(10))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
