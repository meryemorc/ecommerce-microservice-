package com.example.productservice.Dto;

import lombok.Data;

import java.util.List;
@Data
public class CategoryDto{

    private String id;
    private String categoryName;
    private String parentId;
    private Integer level;
    private List<CategoryFieldDTO> fields;


}
