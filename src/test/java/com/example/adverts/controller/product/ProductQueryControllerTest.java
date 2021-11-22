package com.example.adverts.controller.product;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.product.ProductQueryNoParentDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.service.interfaces.product.ProductQueryService;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductQueryController.class)
public class ProductQueryControllerTest {

    @MockBean
    private ProductQueryService productQueryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllProductsOneItemOnly() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        ProductQueryDto productQueryDto = new ProductQueryDto(productId, "product", category, subCategory);
        List<ProductQueryDto> productQueryDtoList = List.of(productQueryDto);

        when(productQueryService.getAllProducts()).thenReturn(productQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/product")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, Object> result = new HashMap<>();
        result.put("products", productQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.products.size()").value(1))
                .andExpect(jsonPath("$.products[0].id").value(productId.toString()))
                .andExpect(jsonPath("$.products[0].title").value("product"))
                .andExpect(jsonPath("$.products[0].category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.products[0].category.title").value("category"))
                .andExpect(jsonPath("$.products[0].subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.products[0].subCategory.title").value("subCategory"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }



    @Test
    public void testGetAllProductsForCategoryAndSubCategoryWhenOneItemOnly() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
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

        ProductQueryNoParentDto productQueryDto = new ProductQueryNoParentDto(productId, "product");
        List<ProductQueryNoParentDto> productQueryDtoList = List.of(productQueryDto);

        when(productQueryService.getAllProductsForCategoryAndSubCategory(categoryId, subCategoryId)).thenReturn(productQueryDtoList);
        when(productQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);
        when(productQueryService.getSubCategory(categoryId)).thenReturn(subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

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
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategory.title").value("subCategory"))
                .andExpect(jsonPath("$.products.size()").value(1))
                .andExpect(jsonPath("$.products[0].id").value(productId.toString()))
                .andExpect(jsonPath("$.products[0].title").value("product"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testGetAllProductsForCategoryAndSubCategoryWhenTwoItems() throws Exception {

        UUID productId1 = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productId2 = UUID.fromString("ad1e7118-2fbe-4cc8-963c-7387d8a13bc7");
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

        ProductQueryNoParentDto productQueryDto1 = new ProductQueryNoParentDto(productId1, "product1");
        ProductQueryNoParentDto productQueryDto2 = new ProductQueryNoParentDto(productId2, "product2");
        List<ProductQueryNoParentDto> productQueryDtoList = List.of(productQueryDto1, productQueryDto2);

        when(productQueryService.getAllProductsForCategoryAndSubCategory(categoryId, subCategoryId)).thenReturn(productQueryDtoList);
        when(productQueryService.getCategory(categoryId)).thenReturn(categoryQueryDto);
        when(productQueryService.getSubCategory(categoryId)).thenReturn(subCategoryQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

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
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.subCategory.title").value("subCategory"))
                .andExpect(jsonPath("$.products[0].id").value(productId1.toString()))
                .andExpect(jsonPath("$.products[0].title").value("product1"))
                .andExpect(jsonPath("$.products[1].id").value(productId2.toString()))
                .andExpect(jsonPath("$.products[1].title").value("product2"))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testGetProductItemForCategoryAndSubCategory() throws Exception {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);
        category.setTitle("category");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setTitle("subCategory");
        subCategory.setCategory(category);

        ProductQueryDto productQueryDto = new ProductQueryDto(productId, "product", category, subCategory);

        when(productQueryService.getProduct(productId)).thenReturn(
                productQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product/{productId}", categoryId, subCategoryId, productId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        String json = asJsonString(productQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.title").value("product"))
                .andExpect(jsonPath("$.category.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.subCategory.id").value(subCategoryId.toString()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
