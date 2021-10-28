package com.example.adverts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@AllArgsConstructor
@Data
public class CategoryOld {

    @Id
    private long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubCategoryOld> subCategoryList;

}
