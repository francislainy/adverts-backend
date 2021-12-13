package com.example.adverts.controller.product;

import jwt.JwtUtil;
import jwt.UserDetailsServiceImpl;
import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentScan({"jwt"})
@WebMvcTest(value = ProductCommandController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class ProductCommandControllerTest {

    Logger logger = LoggerFactory.getLogger(ProductCommandControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductCommandController productCommandController;

    @MockBean
    private ProductCommandService productCommandService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private SubCategoryRepository subCategoryRepository;

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
    void testCreateProductThrows403NoAuthHeader() throws Exception {

        UUID productId = UUID.fromString("78b8f147-c7f1-4b5a-9b52-0c771793bd95");
        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");

        ProductCreateDto productCreateDto = new ProductCreateDto("product", "prod description", "short description", new BigDecimal("100"), category, subCategory);
        ProductCreateDto productCreateResponseDto = new ProductCreateDto(productId, "product", "prod description", "short description", new BigDecimal("100"), category, subCategory);

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(
                productCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateProduct() throws Exception {

        UUID productId = UUID.fromString("78b8f147-c7f1-4b5a-9b52-0c771793bd95");
        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");

        ProductCreateDto productCreateDto = new ProductCreateDto("product", "prod description", "short description", new BigDecimal("100"), category, subCategory);
        ProductCreateDto productCreateResponseDto = new ProductCreateDto(productId, "product", "prod description", "short description", new BigDecimal("100"), category, subCategory);

        String jsonCreate = asJsonString(productCreateDto);
        String jsonResponse = asJsonString(productCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(
                productCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.title").value(productCreateDto.getTitle()))
                .andExpect(jsonPath("$.description").value(productCreateDto.getDescription()))
                .andExpect(jsonPath("$.shortDescription").value(productCreateDto.getShortDescription()))
                .andExpect(jsonPath("$.price").value(productCreateDto.getPrice()))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value(productCreateDto.getCategory().getTitle()))
                .andExpect(jsonPath("$.category.products").doesNotExist())
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategory.title").value(productCreateDto.getSubCategory().getTitle()))
                .andExpect(jsonPath("$.subCategory.category").doesNotExist())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateProductThrowsErrorWhenCategoryDoesNotExist() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductCreateDto productCreateDto = new ProductCreateDto("product", "prod description", "short description", new BigDecimal("100"), category, subCategory);

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(false);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Entity not found\"}", true))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateProductThrowsErrorWhenSubCategoryDoesNotExist() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductCreateDto productCreateDto = new ProductCreateDto("product", "prod description", "short description", new BigDecimal("100"), category, subCategory);

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(false);
        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Entity not found\"}", true))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateProductThrowsErrorWhenMandatoryTitleDoesNotExist() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductCreateDto productCreateDto = new ProductCreateDto(null, "prod description", "short description", new BigDecimal("100"), category, subCategory);

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Missing mandatory field\"}", true))
                .andReturn();


        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateProductAutogenerated() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductCreateDto productCreateDto = new ProductCreateDto();
        productCreateDto.setSubCategory(subCategory);
        productCreateDto.setId(UUID.randomUUID());
        productCreateDto.setTitle("Product");
        productCreateDto.setCategory(category);
        String content = (new ObjectMapper()).writeValueAsString(productCreateDto);
        UUID randomUUIDResult = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", randomUUIDResult,
                        UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productCommandController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

}
