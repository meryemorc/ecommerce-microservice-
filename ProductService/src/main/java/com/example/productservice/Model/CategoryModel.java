package com.example.productservice.Model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Document(collection = "category")
public class CategoryModel {

    @Id
    private String id;

    private String categoryName;
    private String parentId;
    private Integer level;


    private List<CategoryField> fields;
}