package com.example.productservice.Dto.Response;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private String id;
    private String parentId;
    private String categoryName;
    private int level;
}
