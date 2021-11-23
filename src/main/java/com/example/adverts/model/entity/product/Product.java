package com.example.adverts.model.entity.product;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.subcategory.SubCategory;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private BigDecimal price;


    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sub_category_id", referencedColumnName = "id", nullable = false)
    private SubCategory subCategory;


}
