package com.example.productservice.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDto {

    private String id;
    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private Double price;
    private String brand;
    private Integer stock;
    private String color;
    private String size;
    private String sku;

}
