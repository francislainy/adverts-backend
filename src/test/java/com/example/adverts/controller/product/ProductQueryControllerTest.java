package com.example.adverts.controller.product;

import jwt.JwtUtil;
import jwt.UserDetailsServiceImpl;
import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.product.ProductQueryNoParentDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product_address.ProductAddress;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.service.interfaces.product.ProductQueryService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentScan({"jwt"})
@WebMvcTest(value = ProductQueryController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class ProductQueryControllerTest {

    Logger logger = LoggerFactory.getLogger(ProductQueryController.class);

    @MockBean
    private ProductQueryService productQueryService;

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
    void testGetAllProductsThrows403WhenNoAuthHeader() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        ProductQueryDto productQueryDto = new ProductQueryDto(productId, "product", "prod description", "short description", new BigDecimal("100.00"), productAddress, category, subCategory);
        List<ProductQueryDto> productQueryDtoList = List.of(productQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(productQueryService.getAllProducts()).thenReturn(productQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/product")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllProductsOneItemOnly() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        ProductQueryDto productQueryDto = new ProductQueryDto(productId, "product", "prod description", "short description", new BigDecimal("100.00"), productAddress, category, subCategory);
        List<ProductQueryDto> productQueryDtoList = List.of(productQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(productQueryService.getAllProducts()).thenReturn(productQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/product")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("products", productQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.products.size()").value(1))
                .andExpect(jsonPath("$.products[0].id").value(productId.toString()))
                .andExpect(jsonPath("$.products[0].title").value(productQueryDto.getTitle()))
                .andExpect(jsonPath("$.products[0].description").value(productQueryDto.getDescription()))
                .andExpect(jsonPath("$.products[0].shortDescription").value(productQueryDto.getShortDescription()))
                .andExpect(jsonPath("$.products[0].price").value(productQueryDto.getPrice().doubleValue()))
                .andExpect(jsonPath("$.products[0].category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.products[0].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[0].productAddress.address1").value(productAddress.getAddress1()))
                .andExpect(jsonPath("$.products[0].productAddress.address2").value(productAddress.getAddress2()))
                .andExpect(jsonPath("$.products[0].productAddress.address3").value(productAddress.getAddress3()))
                .andExpect(jsonPath("$.products[0].productAddress.city").value(productAddress.getCity()))
                .andExpect(jsonPath("$.products[0].productAddress.state").value(productAddress.getState()))
                .andExpect(jsonPath("$.products[0].productAddress.county").value(productAddress.getCounty()))
                .andExpect(jsonPath("$.products[0].productAddress.country").value(productAddress.getCountry()))
                .andExpect(jsonPath("$.products[0].productAddress.zipcode").value(productAddress.getZipcode()))
                .andExpect(jsonPath("$.products[0].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[0].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[0].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[0].category.title").value(productQueryDto.getCategory().getTitle()))
                .andExpect(jsonPath("$.products[0].subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.products[0].subCategory.title").value(productQueryDto.getSubCategory().getTitle()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllProductsForCategoryAndSubCategoryWhenOneItemOnly() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(categoryId, category.getTitle());

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        SubCategoryQueryNoParentDto subCategoryQueryDto = new SubCategoryQueryNoParentDto(subCategoryId, subCategory.getTitle());

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        ProductQueryNoParentDto productQueryDto = new ProductQueryNoParentDto(productId, "product", "prod description", "short description", new BigDecimal("100.00"), productAddress);
        List<ProductQueryNoParentDto> productQueryDtoList = List.of(productQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(productQueryService.getAllProductsForCategoryAndSubCategory(categoryId, subCategoryId)).thenReturn(productQueryDtoList);
        when(productQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);
        when(productQueryService.getSubCategory(categoryId)).thenReturn(subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("category", categoryQueryDto);
        result.put("subCategory", subCategoryQueryDto);
        result.put("products", productQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value("category"))
                .andExpect(jsonPath("$.category.countSubCategories").doesNotExist())
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategory.title").value("subCategory"))
                .andExpect(jsonPath("$.subCategory.category").doesNotExist())
                .andExpect(jsonPath("$.products.size()").value(1))
                .andExpect(jsonPath("$.products[0].id").value(productId.toString()))
                .andExpect(jsonPath("$.products[0].title").value(productQueryDto.getTitle()))
                .andExpect(jsonPath("$.products[0].description").value(productQueryDto.getDescription()))
                .andExpect(jsonPath("$.products[0].shortDescription").value(productQueryDto.getShortDescription()))
                .andExpect(jsonPath("$.products[0].price").value(productQueryDto.getPrice().doubleValue()))
                .andExpect(jsonPath("$.products[0].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[0].productAddress.address1").value(productAddress.getAddress1()))
                .andExpect(jsonPath("$.products[0].productAddress.address2").value(productAddress.getAddress2()))
                .andExpect(jsonPath("$.products[0].productAddress.address3").value(productAddress.getAddress3()))
                .andExpect(jsonPath("$.products[0].productAddress.city").value(productAddress.getCity()))
                .andExpect(jsonPath("$.products[0].productAddress.state").value(productAddress.getState()))
                .andExpect(jsonPath("$.products[0].productAddress.county").value(productAddress.getCounty()))
                .andExpect(jsonPath("$.products[0].productAddress.country").value(productAddress.getCountry()))
                .andExpect(jsonPath("$.products[0].productAddress.zipcode").value(productAddress.getZipcode()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllProductsForCategoryAndSubCategoryWhenTwoItems() throws Exception {

        UUID productId1 = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productId2 = UUID.fromString("ad1e7118-2fbe-4cc8-963c-7387d8a13bc7");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        CategoryQueryDto categoryQueryDto = new CategoryQueryDto(categoryId, category.getTitle());

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        SubCategoryQueryNoParentDto subCategoryQueryDto = new SubCategoryQueryNoParentDto(subCategoryId, subCategory.getTitle());

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        ProductQueryNoParentDto productQueryDto1 = new ProductQueryNoParentDto(productId1, "product1", "prod description", "short description", new BigDecimal("100.00"), productAddress);
        ProductQueryNoParentDto productQueryDto2 = new ProductQueryNoParentDto(productId2, "product2", "prod description", "short description", new BigDecimal("100.00"), productAddress);
        List<ProductQueryNoParentDto> productQueryDtoList = List.of(productQueryDto1, productQueryDto2);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(productQueryService.getAllProductsForCategoryAndSubCategory(categoryId, subCategoryId)).thenReturn(productQueryDtoList);
        when(productQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);
        when(productQueryService.getSubCategory(categoryId)).thenReturn(subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, Object> result = new HashMap<>();
        result.put("category", categoryQueryDto);
        result.put("subCategory", subCategoryQueryDto);
        result.put("products", productQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.products.size()").value(2))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value("category"))
                .andExpect(jsonPath("$.category.countSubCategories").doesNotExist())
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategory.title").value("subCategory"))
                .andExpect(jsonPath("$.subCategory.category").doesNotExist())
                .andExpect(jsonPath("$.products[0].id").value(productId1.toString()))
                .andExpect(jsonPath("$.products[0].title").value(productQueryDto1.getTitle()))
                .andExpect(jsonPath("$.products[0].description").value(productQueryDto1.getDescription()))
                .andExpect(jsonPath("$.products[0].shortDescription").value(productQueryDto1.getShortDescription()))
                .andExpect(jsonPath("$.products[0].price").value(productQueryDto1.getPrice().doubleValue()))
                .andExpect(jsonPath("$.products[0].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[0].productAddress.address1").value(productAddress.getAddress1()))
                .andExpect(jsonPath("$.products[0].productAddress.address2").value(productAddress.getAddress2()))
                .andExpect(jsonPath("$.products[0].productAddress.address3").value(productAddress.getAddress3()))
                .andExpect(jsonPath("$.products[0].productAddress.city").value(productAddress.getCity()))
                .andExpect(jsonPath("$.products[0].productAddress.state").value(productAddress.getState()))
                .andExpect(jsonPath("$.products[0].productAddress.county").value(productAddress.getCounty()))
                .andExpect(jsonPath("$.products[0].productAddress.country").value(productAddress.getCountry()))
                .andExpect(jsonPath("$.products[0].productAddress.zipcode").value(productAddress.getZipcode()))
                .andExpect(jsonPath("$.products[1].id").value(productId2.toString()))
                .andExpect(jsonPath("$.products[1].title").value(productQueryDto2.getTitle()))
                .andExpect(jsonPath("$.products[1].description").value(productQueryDto2.getDescription()))
                .andExpect(jsonPath("$.products[1].shortDescription").value(productQueryDto2.getShortDescription()))
                .andExpect(jsonPath("$.products[1].price").value(productQueryDto2.getPrice().doubleValue()))
                .andExpect(jsonPath("$.products[1].productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.products[1].productAddress.address1").value(productAddress.getAddress1()))
                .andExpect(jsonPath("$.products[1].productAddress.address2").value(productAddress.getAddress2()))
                .andExpect(jsonPath("$.products[1].productAddress.address3").value(productAddress.getAddress3()))
                .andExpect(jsonPath("$.products[1].productAddress.city").value(productAddress.getCity()))
                .andExpect(jsonPath("$.products[1].productAddress.state").value(productAddress.getState()))
                .andExpect(jsonPath("$.products[1].productAddress.county").value(productAddress.getCounty()))
                .andExpect(jsonPath("$.products[1].productAddress.country").value(productAddress.getCountry()))
                .andExpect(jsonPath("$.products[1].productAddress.zipcode").value(productAddress.getZipcode()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetProductItemForCategoryAndSubCategoryThrows403WhenNoAuthHeader() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        ProductQueryDto productQueryDto = new ProductQueryDto(productId, "product", "prod description", "short description", new BigDecimal("100.0"), productAddress, category, subCategory);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(productQueryService.getProduct(productId)).thenReturn(
                productQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product/{productId}", categoryId, subCategoryId, productId)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetProductItemForCategoryAndSubCategory() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        ProductQueryDto productQueryDto = new ProductQueryDto(productId, "product", "prod description", "short description", new BigDecimal("100.0"), productAddress, category, subCategory);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(productQueryService.getProduct(productId)).thenReturn(
                productQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product/{productId}", categoryId, subCategoryId, productId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        String json = asJsonString(productQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.title").value(productQueryDto.getTitle()))
                .andExpect(jsonPath("$.description").value(productQueryDto.getDescription()))
                .andExpect(jsonPath("$.shortDescription").value(productQueryDto.getShortDescription()))
                .andExpect(jsonPath("$.price").value(productQueryDto.getPrice()))
                .andExpect(jsonPath("$.productAddress.id").value(productAddressId.toString()))
                .andExpect(jsonPath("$.productAddress.address1").value(productQueryDto.getProductAddress().getAddress1()))
                .andExpect(jsonPath("$.productAddress.address2").value(productQueryDto.getProductAddress().getAddress2()))
                .andExpect(jsonPath("$.productAddress.address3").value(productQueryDto.getProductAddress().getAddress3()))
                .andExpect(jsonPath("$.productAddress.city").value(productQueryDto.getProductAddress().getCity()))
                .andExpect(jsonPath("$.productAddress.state").value(productQueryDto.getProductAddress().getState()))
                .andExpect(jsonPath("$.productAddress.county").value(productQueryDto.getProductAddress().getCounty()))
                .andExpect(jsonPath("$.productAddress.country").value(productQueryDto.getProductAddress().getCountry()))
                .andExpect(jsonPath("$.productAddress.zipcode").value(productQueryDto.getProductAddress().getZipcode()))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.category.title").value(category.getTitle()))
                .andExpect(jsonPath("$.category.subCategories").doesNotExist())
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategory.title").value(subCategory.getTitle()))
                .andExpect(jsonPath("$.subCategory.category").doesNotExist())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
