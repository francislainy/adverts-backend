package com.example.adverts.service.product;

import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.impl.category.CategoryCommandImpl;
import com.example.adverts.service.impl.product.ProductCommandImpl;
import com.example.adverts.service.impl.subcategory.SubCategoryCommandImpl;
import com.example.adverts.service.interfaces.category.CategoryCommandService;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import com.example.adverts.service.interfaces.subcategory.SubCategoryCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductCommandService.class)
public class ProductCommandServiceTest {

    @MockBean
    Category category;

    @MockBean
    SubCategory subCategory;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    SubCategoryRepository subCategoryRepository;

    @Mock
    ProductRepository productRepository;

    @MockBean
    private ProductCommandService productCommandService;

    @MockBean
    private CategoryCommandService categoryCommandService;

    @MockBean
    private SubCategoryCommandService subCategoryCommandService;

    @BeforeEach
    void initUseCase() {
        subCategoryCommandService = new SubCategoryCommandImpl(categoryRepository, subCategoryRepository);
        productCommandService = new ProductCommandImpl(productRepository, categoryRepository, subCategoryRepository);
        categoryCommandService = new CategoryCommandImpl(categoryRepository);
    }

    @Test
    public void testProductItemSavedToDb() {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        Product product = new Product();
        product.setId(productId);

        Product productMocked = new Product(productId, "product", "prod description", new BigDecimal("100"), category, subCategory);

        when(productRepository.save(any(Product.class))).thenReturn(productMocked);
        when(categoryRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(category));
        when(subCategoryRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(subCategory));

        ProductCreateDto productCreateDto = new ProductCreateDto(productId, "product", "prod description", new BigDecimal("100"), categoryId, subCategoryId);
        productCreateDto = productCommandService.createProduct(productCreateDto, categoryId, subCategoryId);

        assertNotNull(productCreateDto);
        assertEquals(productMocked.getId(), productCreateDto.getId());
        assertEquals(productMocked.getTitle(), productCreateDto.getTitle());
        assertEquals(productMocked.getDescription(), productCreateDto.getDescription());
        assertEquals(productMocked.getPrice(), productCreateDto.getPrice());
        assertEquals(productMocked.getCategory().getId(), productCreateDto.getCategoryId());
        assertEquals(productMocked.getSubCategory().getId(), productCreateDto.getSubCategoryId());
    }

}
