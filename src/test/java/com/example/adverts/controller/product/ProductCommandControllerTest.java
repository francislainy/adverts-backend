package com.example.adverts.controller.product;

import com.example.adverts.controller.category.CategoryCommandController;
import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {ProductCommandController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryCommandController.class)
class ProductCommandControllerTest {

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

    @Test
    void testCreateProduct() throws Exception {

        UUID productId = UUID.fromString("78b8f147-c7f1-4b5a-9b52-0c771793bd95");
        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        ProductCreateDto productCreateDto = new ProductCreateDto("product");
        ProductCreateDto productCreateResponseDto = new ProductCreateDto(productId, "product", "prod description", new BigDecimal("100"), categoryId, subCategoryId);

        String jsonCreate = asJsonString(productCreateDto);
        String jsonResponse = asJsonString(productCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);

        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(
                productCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.title").value("product"))
                .andExpect(jsonPath("$.description").value("prod description"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("100")))
                .andExpect(jsonPath("$.subCategoryId").value(subCategoryId.toString()))
                .andExpect(jsonPath("$.categoryId").value(categoryId.toString()))
                .andReturn();


        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testCreateProductThrowsErrorWhenCategoryDoesNotExist() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        ProductCreateDto productCreateDto = new ProductCreateDto("product");

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryRepository.existsById(any(UUID.class))).thenReturn(false);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);

        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Entity not found\"}", true))
                .andReturn();


        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testCreateProductThrowsErrorWhenSubCategoryDoesNotExist() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        ProductCreateDto productCreateDto = new ProductCreateDto("product");

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(false);

        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Entity not found\"}", true))
                .andReturn();


        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testCreateProductThrowsErrorWhenTitleDoesNotExist() throws Exception {

        UUID subCategoryId = UUID.fromString("067fe1bb-6378-4493-a83b-629c304994dc");
        UUID categoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        ProductCreateDto productCreateDto = new ProductCreateDto();

        String jsonCreate = asJsonString(productCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/category/{categoryId}/subCategory/{subCategoryId}/product", categoryId, subCategoryId)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(subCategoryRepository.existsById(any(UUID.class))).thenReturn(true);

        when(productCommandService.createProduct(productCreateDto, categoryId, subCategoryId)).thenReturn(any());

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Missing title\"}", true))
                .andReturn();


        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testCreateProductAutogenerated() throws Exception {
        ProductCreateDto productCreateDto = new ProductCreateDto();
        productCreateDto.setSubCategoryId(UUID.randomUUID());
        productCreateDto.setId(UUID.randomUUID());
        productCreateDto.setTitle("Product");
        productCreateDto.setCategoryId(UUID.randomUUID());
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
