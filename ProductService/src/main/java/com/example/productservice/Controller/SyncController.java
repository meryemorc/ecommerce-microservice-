package com.example.productservice.Controller;

import com.example.productservice.Service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor //otomatik constructor olusturuyor
public class SyncController {

    private final ProductService productService;

    @PostMapping("/products")
    public String syncProducts() {
        productService.syncAllProductsToElasticsearch();
        return "Tüm ürünler Elasticsearch'e kopyalandı!";
    }


}
