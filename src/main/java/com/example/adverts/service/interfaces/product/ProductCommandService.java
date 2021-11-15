package com.example.adverts.service.interfaces.product;

import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryCreateDto;
import com.example.adverts.model.dto.subcategory.SubCategoryUpdateDto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductCommandService {

    ProductCreateDto createProduct(ProductCreateDto productCreateDto, UUID categoryId, UUID subCategoryId);
}
