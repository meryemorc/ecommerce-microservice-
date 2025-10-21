package com.example.productservice.Model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "products")
public class ProductModel {

    @Id
    private String id;

    private String name;
    private String description;
    private String categoryId;
    private Double price;
    private String brand;
    private Integer stock;
    private String color;

    private Map<String, Object> attributes;
}