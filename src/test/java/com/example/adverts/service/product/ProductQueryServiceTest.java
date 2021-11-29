package com.example.adverts.service.product;

import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.product.ProductQueryNoParentDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.product_address.ProductAddress;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.service.impl.product.ProductQueryServiceImpl;
import com.example.adverts.service.interfaces.product.ProductQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductQueryService.class)
class ProductQueryServiceTest {

    @MockBean
    Category category;

    @MockBean
    SubCategory subCategory;

    @MockBean
    ProductAddress productAddress;

    @Mock
    ProductRepository productRepository;

    @MockBean
    private ProductQueryService productQueryService;

    @BeforeEach
    void initUseCase() {

        productQueryService = new ProductQueryServiceImpl(productRepository);
    }


    @Test
    void testGetProductForSubCategoryAndCategory() {

        Product productMocked = new Product(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"), "product", "prod description", "short description", new BigDecimal("100.0"), productAddress, category, subCategory);

        when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(productMocked));

        ProductQueryDto productQueryDto = productQueryService.getProduct(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(productQueryDto);
        assertEquals(productMocked.getId(), productQueryDto.getId());
        assertEquals(productMocked.getTitle(), productQueryDto.getTitle());
        assertEquals(productMocked.getDescription(), productQueryDto.getDescription());
        assertEquals(productMocked.getShortDescription(), productQueryDto.getShortDescription());
        assertEquals(productMocked.getPrice(), productQueryDto.getPrice());
        assertEquals(productMocked.getProductAddress(), productQueryDto.getProductAddress());
        assertEquals(productMocked.getCategory(), productQueryDto.getCategory());
        assertEquals(productMocked.getSubCategory(), productQueryDto.getSubCategory());
    }


    @Test
    void testGetAllProducts() {

        UUID productId1 = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        Product productMocked1 = new Product(productId1, "product1", "prod description", "short description", new BigDecimal("100"), productAddress, category, subCategory);
        Product productMocked2 = new Product(productId2, "product2", "prod description", "short description", new BigDecimal("100"), productAddress, category, subCategory);

        List<Product> productMockedList = List.of(productMocked1, productMocked2);

        when(productRepository.findAll()).thenReturn(productMockedList);

        List<ProductQueryDto> productQueryDtoList = productQueryService.getAllProducts();

        assertNotNull(productQueryDtoList);
        assertEquals(productMockedList.size(), productQueryDtoList.size());
        assertEquals(productMockedList.get(0).getId(), productQueryDtoList.get(0).getId());
        assertEquals(productMockedList.get(0).getTitle(), productQueryDtoList.get(0).getTitle());
        assertEquals(productMockedList.get(0).getDescription(), productQueryDtoList.get(0).getDescription());
        assertEquals(productMockedList.get(0).getPrice(), productQueryDtoList.get(0).getPrice());
        assertEquals(productMockedList.get(0).getCategory(), productQueryDtoList.get(0).getCategory());
        assertEquals(productMockedList.get(0).getSubCategory(), productQueryDtoList.get(0).getSubCategory());
        assertEquals(productMockedList.get(1).getId(), productQueryDtoList.get(1).getId());
        assertEquals(productMockedList.get(1).getTitle(), productQueryDtoList.get(1).getTitle());
        assertEquals(productMockedList.get(1).getDescription(), productQueryDtoList.get(1).getDescription());
        assertEquals(productMockedList.get(1).getPrice(), productQueryDtoList.get(1).getPrice());
        assertEquals(productMockedList.get(1).getCategory(), productQueryDtoList.get(1).getCategory());
        assertEquals(productMockedList.get(1).getSubCategory(), productQueryDtoList.get(1).getSubCategory());
    }


    @Test
    void testGetProductsForCategoryAndSubCategory() {

        UUID productId1 = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID productId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");
        UUID productAddressId = UUID.fromString("4c358df7-4a38-4ad0-b070-59adcd57dde0");
        UUID categoryId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UUID subCategoryId = UUID.fromString("2483d126-0e02-419f-ac34-e48bfced8cf5");

        Category category = new Category();
        category.setId(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        ProductAddress productAddress = new ProductAddress(productAddressId, "address1", "address2", "address3", "city", "state", "county", "country", "zipcode", null);

        Product productMocked1 = new Product(productId1, "product1", "prod description", "short description", new BigDecimal("100"), productAddress, category, subCategory);
        Product productMocked2 = new Product(productId2, "product2", "prod description", "short description", new BigDecimal("100"), productAddress, category, subCategory);

        List<Product> productMockedList = List.of(productMocked1, productMocked2);

        when(productRepository.findAll()).thenReturn(productMockedList);

        List<ProductQueryNoParentDto> productQueryDtoList = productQueryService.getAllProductsForCategoryAndSubCategory(categoryId, subCategoryId);

        assertNotNull(productQueryDtoList);
        assertEquals(productMockedList.size(), productQueryDtoList.size());
        assertEquals(productMockedList.get(0).getId(), productQueryDtoList.get(0).getId());
        assertEquals(productMockedList.get(0).getTitle(), productQueryDtoList.get(0).getTitle());
        assertEquals(productMockedList.get(0).getDescription(), productQueryDtoList.get(0).getDescription());
        assertEquals(productMockedList.get(0).getShortDescription(), productQueryDtoList.get(0).getShortDescription());
        assertEquals(productMockedList.get(0).getPrice().doubleValue(), productQueryDtoList.get(0).getPrice().doubleValue());
        assertEquals(productMockedList.get(1).getId(), productQueryDtoList.get(1).getId());
        assertEquals(productMockedList.get(1).getTitle(), productQueryDtoList.get(1).getTitle());
        assertEquals(productMockedList.get(1).getDescription(), productQueryDtoList.get(1).getDescription());
        assertEquals(productMockedList.get(1).getShortDescription(), productQueryDtoList.get(1).getShortDescription());
        assertEquals(productMockedList.get(1).getPrice().doubleValue(), productQueryDtoList.get(1).getPrice().doubleValue());
    }


    @Test
    void testCreateProduct() {

        UUID productId = UUID.fromString("ac358df7-4a38-4ad0-b070-59adcd57dde0");
        Product productMocked = new Product(productId, "product", "prod description", "short description", new BigDecimal("100"), productAddress, category, subCategory);

        when(productRepository.save(productMocked)).thenReturn(productMocked);

        Product product = productRepository.save(productMocked);

        assertNotNull(product);
    }

}
