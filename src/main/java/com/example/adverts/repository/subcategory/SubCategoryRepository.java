package com.example.adverts.repository.subcategory;

import com.example.adverts.model.entity.subcategory.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, UUID> {
}
