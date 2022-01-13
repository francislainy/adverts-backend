package com.example.adverts.repository.subcategory;

import com.example.adverts.model.entity.subcategory.SubCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, UUID> {

    List<SubCategory> findByOrderByTitle();

    @Query("select size(s.products) from SubCategory s where s.id=:parentID")
    Long countProducts(@Param("parentID") UUID parentID);
}
