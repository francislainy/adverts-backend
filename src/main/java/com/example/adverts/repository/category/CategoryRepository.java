package com.example.adverts.repository.category;

import com.example.adverts.model.entity.category.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    List<Category> findByOrderByTitle();

    @Query("select size(c.subCategories) from Category c where c.id=:parentID")
    Long countSubCategories(@Param("parentID") UUID parentID);

    @Query("select size(c.products) from Category c where c.id=:parentID")
    Long countProducts(@Param("parentID") UUID parentID);
}
