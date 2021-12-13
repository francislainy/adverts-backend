package com.example.adverts.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryCreateDto implements Serializable {

    private UUID id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    public CategoryCreateDto(String title) {
        this.title = title;
    }

}
