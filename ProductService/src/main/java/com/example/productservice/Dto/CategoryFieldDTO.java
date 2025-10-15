package com.example.productservice.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryFieldDTO {
    private String fieldName;
    private String fieldLabel;
    private String fieldType;
    private Boolean required;
    private List<String> options;
    private Integer minValue;
    private Integer maxValue;
    private Integer minLength;
    private Integer maxLength;
}
