package com.example.productservice.Dto.Request;


import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryRequestDto {

    private String parentId;

    @NotBlank
    private String categoryName;

    private int level;
}
