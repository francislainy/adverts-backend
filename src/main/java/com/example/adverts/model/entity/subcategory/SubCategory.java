package com.example.adverts.model.entity.subcategory;

import com.example.adverts.model.entity.category.Category;
import com.example.adverts.model.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "subcategory")
public class SubCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;

//    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Product> products;
}
