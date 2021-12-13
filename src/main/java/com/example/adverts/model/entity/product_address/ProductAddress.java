package com.example.adverts.model.entity.product_address;

import com.example.adverts.model.entity.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "product_address")
public class ProductAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "address3")
    private String address3;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "county")
    private String county;
    @Column(name = "country")
    private String country;
    @Column(name = "zipcode")
    private String zipcode;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Product> products;

}
