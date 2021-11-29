package com.example.adverts.repository.product_address;

import com.example.adverts.model.entity.product_address.ProductAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductAddressRepository extends CrudRepository<ProductAddress, UUID> {
}
