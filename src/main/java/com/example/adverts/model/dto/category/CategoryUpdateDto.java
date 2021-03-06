package com.example.adverts.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryUpdateDto implements Serializable {

    private UUID id;
    private String title;

    public CategoryUpdateDto(String title) {
        this.title = title;
    }
}
