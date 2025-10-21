package com.example.productservice.Controller;

import com.example.productservice.Dto.ProductDto;
import com.example.productservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }
    @GetMapping("/name")
    public ProductDto getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }
    @GetMapping("/brand")
    public ProductDto getProductByBrand(@RequestParam String brand) {
        return productService.getProductByBrand(brand);
    }
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductCategory(@PathVariable String categoryName) {
        List<ProductDto> productDto = productService.getProductCategoryName(categoryName);
        return ResponseEntity.ok(productDto);
    }
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDto = productService.getAllProducts();
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/{productId}/check-stock")
    public ResponseEntity<Boolean> checkStock(@PathVariable String productId,
                                              @RequestParam Integer quantity){
        try{
            boolean hasStock = productService.checkCtock(productId, quantity);
            return ResponseEntity.ok(hasStock);
        }
        catch(Exception e){
            return ResponseEntity.ok(false);
        }
    }
}
