package com.example.adverts.service.impl.product;

import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCommandImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ProductCommandImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public ProductCreateDto createProduct(ProductCreateDto productCreateDto, UUID categoryId, UUID subCategoryId) {

        if (categoryRepository.findById(categoryId).isPresent() && subCategoryRepository.findById(subCategoryId).isPresent()) {

            Product product = new Product();
            product.setSubCategory(subCategoryRepository.findById(subCategoryId).get());
            product.setCategory(categoryRepository.findById(categoryId).get());
            product.setTitle(productCreateDto.getTitle());

            product = productRepository.save(product);

            return new ProductCreateDto(product.getId(), product.getTitle(),  product.getCategory().getId(), product.getSubCategory().getId());
        } else {
            return null;
        }

    }

}
