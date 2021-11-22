package com.example.adverts.service.product;

import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.product.ProductQueryNoParentDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.service.impl.product.ProductQueryServiceImpl;
import com.example.adverts.service.interfaces.product.ProductQueryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductQueryService.class)
public class ProductQueryServiceTest {

    @MockBean
    Category category;

    @MockBean
    SubCategory subCategory;

    @Mock
    ProductRepository productRepository;

    @MockBean
    private ProductQueryService productQueryService;


    @BeforeAll
    static void initData() {
//        category = new Category();
//        category.setId(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));
//
//        subCategory = new SubCategory();
//        subCategory.setId(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));
    }

    @BeforeEach
    void initUseCase() {

        productQueryService = new ProductQueryServiceImpl(productRepository);
    }



    @Test
    public void testGetProduct() {

//        Category category = new Category();
//        category.setId(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));
//
//        SubCategory subCategory = new SubCategory();
//        subCategory.setId(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        Product productMocked = new Product(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "subCategory", category, subCategory);

        when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(productMocked));

        ProductQueryDto productQueryDto = productQueryService.getProduct(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(productQueryDto);
        assertEquals(productMocked.getId(), productQueryDto.getId());
        assertEquals(productMocked.getTitle(), productQueryDto.getTitle());
    }


    @Test
    public void testGetMultipleProducts() {

        UUID productId1 = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        Product productMocked1 = new Product(productId1, "product1", category, subCategory);
        Product productMocked2 = new Product(productId2, "product2", category, subCategory);

        List<Product> productMockedList = List.of(productMocked1, productMocked2);

        when(productRepository.findAll()).thenReturn(productMockedList);

        List<ProductQueryNoParentDto> productQueryDtoList = productQueryService.getAllProductsForCategoryAndSubCategory(categoryId, subCategoryId);

        assertNotNull(productQueryDtoList);
        assertEquals(productMockedList.size(), productQueryDtoList.size());
        assertEquals(productMockedList.get(0).getId(), productQueryDtoList.get(0).getId());
        assertEquals(productMockedList.get(0).getTitle(), productQueryDtoList.get(0).getTitle());
        assertEquals(productMockedList.get(1).getId(), productQueryDtoList.get(1).getId());
        assertEquals(productMockedList.get(1).getTitle(), productQueryDtoList.get(1).getTitle());
    }


    @Test
    public void testCreateProduct() {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        Product productMocked = new Product(productId, "product", category, subCategory);

        when(productRepository.save(productMocked)).thenReturn(productMocked);

        Product product = productRepository.save(productMocked);

        assertNotNull(product);
    }

}
