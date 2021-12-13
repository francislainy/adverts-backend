package com.example.adverts.service.impl.product;

import com.example.adverts.model.dto.product.ProductCreateDto;
import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.example.adverts.repository.category.CategoryRepository;
import com.example.adverts.repository.product.ProductRepository;
import com.example.adverts.repository.subcategory.SubCategoryRepository;
import com.example.adverts.service.interfaces.product.ProductCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(subCategoryId);

        if (optionalCategory.isPresent() && optionalSubCategory.isPresent()) {

            Category category = optionalCategory.get();
            SubCategory subCategory = optionalSubCategory.get();

            Product product = new Product();
            product.setTitle(productCreateDto.getTitle());
            product.setCategory(category);
            product.setSubCategory(subCategory);

            product = productRepository.save(product);

            return new ProductCreateDto(product.getId(), product.getTitle(), product.getDescription(), product.getShortDescription(), product.getPrice(), category, subCategory);
        } else {
            return null;
        }

    }

}
