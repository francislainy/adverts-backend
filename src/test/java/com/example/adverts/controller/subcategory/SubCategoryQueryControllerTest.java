package com.example.adverts.controller.subcategory;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = SubCategoryQueryController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class SubCategoryQueryControllerTest {

    Logger logger = LoggerFactory.getLogger(SubCategoryQueryController.class);

    @MockBean
    private SubCategoryQueryService subCategoryQueryService;

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
    void testGetAllSubCategoriesForCategoryWhenOneItemOnly() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryNoParentDto subCategoryQueryDto = new SubCategoryQueryNoParentDto(subCategoryId, "subCategory");
        List<SubCategoryQueryNoParentDto> subCategoryQueryDtoList = List.of(subCategoryQueryDto);

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(category.getId(), category.getTitle(), (long) subCategoryQueryDtoList.size());

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryQueryService.getAllSubCategories(any())).thenReturn(subCategoryQueryDtoList);
        when(subCategoryQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("category", categoryQueryDto);
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value("category"))
                .andExpect(jsonPath("$.category.countSubCategories").value(subCategoryQueryDtoList.size()))
                .andExpect(jsonPath("$.subCategories.size()").value(1))
                .andExpect(jsonPath("$.subCategories[0].id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetAllSubCategoriesForCategoryWhenTwoItems() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId1 = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryNoParentDto subCategoryQueryDto1 = new SubCategoryQueryNoParentDto(subCategoryId1, "subCategory1");
        SubCategoryQueryNoParentDto subCategoryQueryDto2 = new SubCategoryQueryNoParentDto(subCategoryId2, "subCategory2");
        List<SubCategoryQueryNoParentDto> subCategoryQueryDtoList = List.of(subCategoryQueryDto1, subCategoryQueryDto2);

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(category.getId(), category.getTitle(), (long) subCategoryQueryDtoList.size());

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryQueryService.getAllSubCategories(categoryId)).thenReturn(subCategoryQueryDtoList);
        when(subCategoryQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory", categoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("category", categoryQueryDto);
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.subCategories.size()").value(2))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value("category"))
                .andExpect(jsonPath("$.category.countSubCategories").value(subCategoryQueryDtoList.size()))
                .andExpect(jsonPath("$.subCategories[0].id").value(subCategoryId1.toString()))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory1"))
                .andExpect(jsonPath("$.subCategories[1].id").value(subCategoryId2.toString()))
                .andExpect(jsonPath("$.subCategories[1].title").value("subCategory2"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetAllSubCategories() throws Exception {

        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryDto subCategoryQueryDto = new SubCategoryQueryDto(subCategoryId, "subCategory", category);
        List<SubCategoryQueryDto> subCategoryQueryDtoList = List.of(subCategoryQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryQueryService.getAllSubCategories()).thenReturn(subCategoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/subCategory")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("subCategories", subCategoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.subCategories.size()").value(1))
                .andExpect(jsonPath("$.subCategories[0].category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.subCategories[0].category.title").value("category"))
                .andExpect(jsonPath("$.subCategories[0].id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategories[0].title").value("subCategory"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetSubCategoryItemForCategory() throws Exception {

        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");
        UUID subCategoryId = UUID.fromString("3ba4002a-31c5-4cc7-9b92-cbf0db998c41");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategoryQueryDto subCategoryQueryDto = new SubCategoryQueryDto(subCategoryId, "subCategory", category);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(subCategoryQueryService.getSubCategory(subCategoryId, categoryId)).thenReturn(subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        String json = asJsonString(subCategoryQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.title").value("subCategory"))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value("category"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
