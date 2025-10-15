package com.example.productservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    // Genel arama (basit kullanım)
    private String keyword;      // Hem name hem description
    private String name;
    private String description;
    private String brand;
    private String categoryId;
    private String color;
    private String size;
    }


