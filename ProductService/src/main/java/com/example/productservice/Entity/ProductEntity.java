package com.example.productservice.Entity;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import java.util.Map;

@Data
@Document(collection = "products")
public class ProductEntity {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private String categoryId;
    private Double price;
    private String brand;
    private Integer stock;
    private String color;

    private Map<String, Object> attributes;
}