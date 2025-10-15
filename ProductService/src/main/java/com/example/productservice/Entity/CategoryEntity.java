package com.example.productservice.Entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Document(collection = "category")
public class CategoryEntity {

    @Id
    private String id;

    private String categoryName;
    private String parentId;
    private Integer level;


    private List<CategoryField> fields;
}