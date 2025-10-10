package com.example.productservice.Entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@Data
@Document(collection = "category")
public class CategoryEntity {

    @Id
    private String id;
    private String parentId;
    private String categoryName;
    private int level;


}
