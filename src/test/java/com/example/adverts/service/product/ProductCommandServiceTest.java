package com.example.adverts.service.product;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.product_address.ProductAddress;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductCommandService.class)
class ProductCommandServiceTest {

    @MockBean
    Category category;

    @MockBean
    SubCategory subCategory;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    SubCategoryRepository subCategoryRepository;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    private ProductCommandService productCommandService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testProductItemCreated() {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        Product product = new Product();
        product.setId(productId);

        Product productMocked = new Product(productId, "product", "prod description", "short description", new BigDecimal("100"), productAddress, category, subCategory);

        when(productRepository.save(any(Product.class))).thenReturn(productMocked);
        when(categoryRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(category));
        when(subCategoryRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(subCategory));

        ProductCreateDto productCreateDto = new ProductCreateDto(productId, "product", "prod description", "short description", new BigDecimal("100"), category, subCategory);
        productCreateDto = productCommandService.createProduct(productCreateDto, categoryId, subCategoryId);

        assertNotNull(productCreateDto);
        assertEquals(productMocked.getId(), productCreateDto.getId());
        assertEquals(productMocked.getTitle(), productCreateDto.getTitle());
        assertEquals(productMocked.getDescription(), productCreateDto.getDescription());
        assertEquals(productMocked.getShortDescription(), productCreateDto.getShortDescription());
        assertEquals(productMocked.getPrice(), productCreateDto.getPrice());
        assertEquals(productMocked.getCategory(), productCreateDto.getCategory());
        assertEquals(productMocked.getProductAddress(), productAddress);
        assertEquals(productMocked.getSubCategory(), productCreateDto.getSubCategory());
    }

}
