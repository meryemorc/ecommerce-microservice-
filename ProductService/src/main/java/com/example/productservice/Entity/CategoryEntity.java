package com.example.productservice.Entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;


@Data
@Document(collection = "category")
public class CategoryEntity {

    @Id
    private String id;
    private String parentId;
    private String categoryName;
    private List<String> filters;
    // ram, kamera, hafıza, model, bla bla
    // kozmetık renk kod, tıp ruj sampuan dısmacunu vsvs ,
    // supermarket boyut marka
    private int level;


}
