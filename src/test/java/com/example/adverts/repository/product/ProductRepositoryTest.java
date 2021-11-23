package com.example.adverts.repository.product;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.subcategory.SubCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findOne() {

        Category category = new Category();
        category.setId(UUID.randomUUID());
        SubCategory subCategory = new SubCategory();
        subCategory.setId(UUID.randomUUID());
        subCategory.setCategory(category);

        Product product = new Product();
        product.setCategory(category);
        product.setSubCategory(subCategory);
        product = productRepository.save(product);
        Product fetchedProduct = productRepository.findById(product.getId()).get();

        assertNotNull(fetchedProduct);
    }

    @Test
    void findAll() {

        UUID productId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");

        Category category = new Category();
        category.setId(UUID.randomUUID());
        SubCategory subCategory = new SubCategory();
        subCategory.setId(UUID.randomUUID());
        subCategory.setCategory(category);

        List<Product> productList = List.of(
                new Product(productId, "product", "prod description", new BigDecimal("100"), category, subCategory)
        );
        productRepository.saveAll(productList);
        List<Product> allProducts = (List<Product>) productRepository.findAll();
        assertTrue(allProducts.size() > 0);
    }

}
