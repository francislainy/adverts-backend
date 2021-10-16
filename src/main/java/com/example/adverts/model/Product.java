package com.example.adverts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {

    private long id;
    private String name;
    private String description;
    private String profileImageUrl;
    private Double price;
    private String location; // todo: location class

}
