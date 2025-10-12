package com.example.productservice.Entity;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

@Data
@Document(collection = "products")
public class ProductEntity {

    @Id
    private ObjectId id;//mongo objectID kullanıyor

    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private Double price;
    private String brand;
    private int stock;
    private String color;
    private String size;

}
