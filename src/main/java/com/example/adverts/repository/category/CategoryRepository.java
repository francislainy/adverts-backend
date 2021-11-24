package com.example.adverts.repository.category;

import com.example.adverts.model.entity.category.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    @Query("select size(c.subCategories) from Category c where c.id=:parentID")
    Long findAllChildrenCount(@Param("parentID") UUID parentID);
}
