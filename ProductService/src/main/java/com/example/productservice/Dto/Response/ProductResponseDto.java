package com.example.productservice.Dto.Response;

import lombok.Data;

@Data
public class ProductResponseDto {

    private String id; // mongonun oluşturduğu otomatik id'yi içerir.
    private String name;
    private String description;
    private String categoryName;
    private String categoryId;
    private Double price;
    private String brand;
    private int stock;
    private String color;
    private String size;
    private Integer weight;
    private String sku;
}
