package com.example.adverts.service.interfaces.product;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.product.ProductQueryNoParentDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductQueryService {

    ProductQueryDto getProduct(UUID id);

    List<ProductQueryNoParentDto> getAllProducts(UUID categoryId, UUID subCategoryId);

    CategoryQueryDto getCategory(UUID categoryId);

    SubCategoryQueryNoParentDto getSubCategory(UUID categoryId);

}
