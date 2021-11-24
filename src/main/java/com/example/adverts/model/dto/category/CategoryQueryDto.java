package com.example.adverts.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryQueryDto implements Serializable {

    private UUID id;
    private String title;
    private Long countSubCategories;

    public CategoryQueryDto(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

}
