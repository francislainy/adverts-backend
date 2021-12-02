package com.example.adverts.service.impl.product;

import com.example.adverts.model.dto.category.CategoryQueryDto;
import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.product.ProductQueryNoParentDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryNoParentDto;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.service.interfaces.product.ProductQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductQueryDto getProduct(UUID id) {

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            Product product = optional.get();

            return new ProductQueryDto(product.getId(), product.getTitle(), product.getDescription(), product.getShortDescription(), product.getPrice(), product.getProductAddress(), product.getCategory(), product.getSubCategory());

        } else {
            return null;
        }
    }

    @Override
    public List<ProductQueryDto> getAllProducts() {
        List<ProductQueryDto> productList = new ArrayList<>();

        productRepository.findAll().forEach(product ->
                productList.add(new ProductQueryDto(product.getId(), product.getTitle(), product.getDescription(), product.getShortDescription(), product.getPrice(), product.getProductAddress(), product.getCategory(), product.getSubCategory())));

        return productList;
    }

    @Override
    public List<ProductQueryNoParentDto> getAllProductsForCategoryAndSubCategory(UUID categoryId, UUID subCategoryId) {
        List<ProductQueryNoParentDto> productList = new ArrayList<>();

        productRepository.findAll().forEach(product -> {

            if (product.getCategory().getId().equals(categoryId) && product.getSubCategory().getId().equals(subCategoryId)) {
                productList.add(new ProductQueryNoParentDto(product.getId(), product.getTitle(), product.getDescription(), product.getShortDescription(), product.getPrice(), product.getProductAddress()));
            }

        });

        return productList;
    }

    @Override
    public CategoryQueryDto getCategory(UUID categoryId) {
        CategoryQueryDto categoryQueryDto = new CategoryQueryDto();

        for (Product product : productRepository.findAll()) {
            if (product.getCategory().getId().equals(categoryId)) {
                categoryQueryDto = new CategoryQueryDto(product.getCategory().getId(), product.getCategory().getTitle());
                break;
            }
        }

        return categoryQueryDto;
    }

    @Override
    public SubCategoryQueryNoParentDto getSubCategory(UUID categoryId) {
        SubCategoryQueryNoParentDto subCategoryQueryDto = new SubCategoryQueryNoParentDto();

        for (Product product : productRepository.findAll()) {
            if (product.getCategory().getId().equals(categoryId)) {
                subCategoryQueryDto = new SubCategoryQueryNoParentDto(product.getSubCategory().getId(), product.getSubCategory().getTitle());
                break;
            }
        }

        return subCategoryQueryDto;
    }

}


