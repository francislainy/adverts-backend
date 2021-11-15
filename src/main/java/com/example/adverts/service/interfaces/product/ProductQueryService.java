package com.example.adverts.service.interfaces.product;

import com.example.adverts.model.dto.product.ProductQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductQueryService {

    ProductQueryDto getProduct(UUID id);

    List<ProductQueryDto> getAllProducts();

}
