package com.example.productservice.Dto;

import com.example.productservice.Dto.CategoryDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDto {

    private String id;
    @NotBlank(message = "ürün adı zorunludur.")
    @Size(min = 2, max = 100)
    private String name;

    private String description;
    private String categoryId;
    private String categoryName;

    @Positive(message = "fiyat pozitif olmalıdır.")
    private Double price;
    private CategoryDto category;
    private String brand;

    @Min(value = 0, message = "stok negatif olamaz")
    private int stock;

    private String color;
    private String size;

    @Min(value = 0, message = "Ağırlık negatif olamaz")
    private int weight;
    private String sku;
}
