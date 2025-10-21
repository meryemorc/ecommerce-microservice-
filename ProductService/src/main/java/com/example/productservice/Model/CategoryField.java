package com.example.productservice.Model;

import lombok.Data;
import java.util.List;

@Data
public class CategoryField {
        private String fieldName;        // "ram", "size", "author"
        private String fieldLabel;       // "RAM", "Beden", "Yazar"
        private String fieldType;        // "select", "text", "number"
        private Boolean required;        // Zorunlu mu

        // Select için seçenekler
        private List<String> options;    // ["4GB", "6GB", "8GB"]

        // Number için min-max
        private Integer minValue;
        private Integer maxValue;

        // Text için min-max uzunluk
        private Integer minLength;
        private Integer maxLength;
    }


