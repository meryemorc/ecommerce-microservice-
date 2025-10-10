package com.example.productservice.Controller;

import com.example.productservice.Dto.Response.ProductResponseDto;
import com.example.productservice.Repository.CategoryRepository;
import com.example.productservice.Service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return productService.getProductById(objectId);
    }
    @GetMapping("/name")
    public ProductResponseDto getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }
    @GetMapping("/brand")
    public ProductResponseDto getProductByBrand(@RequestParam String brand) {
        return productService.getProductByBrand(brand);
    }
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductResponseDto>> getProductCategory(@PathVariable String categoryName) {
        List<ProductResponseDto> productDto = productService.getProductCategoryName(categoryName);
        return ResponseEntity.ok(productDto);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productDto = productService.getAllProducts();
        return ResponseEntity.ok(productDto);
    }
}
