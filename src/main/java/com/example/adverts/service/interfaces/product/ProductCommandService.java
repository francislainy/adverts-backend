package com.example.adverts.service.interfaces.product;

import com.example.adverts.model.dto.product.ProductCreateDto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductCommandService {

    ProductCreateDto createProduct(ProductCreateDto productCreateDto, UUID categoryId, UUID subCategoryId);
}
