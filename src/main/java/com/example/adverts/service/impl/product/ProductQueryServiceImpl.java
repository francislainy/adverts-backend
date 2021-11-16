package com.example.adverts.service.impl.product;

import com.example.adverts.model.dto.product.ProductQueryDto;
import com.example.adverts.model.dto.subcategory.SubCategoryQueryDto;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductQueryService;
import com.example.adverts.service.interfaces.subcategory.SubCategoryQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductQueryDto getProduct(UUID id) {
        if (productRepository.findById(id).isPresent()) {
            Product product = productRepository.findById(id).get();

            return new ProductQueryDto(product.getId(), product.getTitle(), product.getCategory(), product.getSubCategory());

        } else {
            return null;
        }
    }

    @Override
    public List<ProductQueryDto> getAllProducts() {
        List<ProductQueryDto> productList = new ArrayList<>();

        productRepository.findAll().forEach(product -> {
            productList.add(new ProductQueryDto(product.getId(), product.getTitle(), product.getCategory(), product.getSubCategory()));
        });

        return productList;
    }
}


