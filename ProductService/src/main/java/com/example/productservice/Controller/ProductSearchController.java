package com.example.productservice.Controller;

import com.example.productservice.Model.ProductModel;
import com.example.productservice.Service.ProductSearchService;
import com.example.productservice.Service.ProductSearchService.BrandStatistics;
import com.example.productservice.Service.ProductSearchService.CategoryCount;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/api/products/search")
    public class ProductSearchController {

        @Autowired
        private ProductSearchService searchService;


        @GetMapping("/category/{categoryId}")
        public ResponseEntity<List<ProductModel>> searchByCategory(@PathVariable String categoryId) {
            List<ProductModel> products = searchService.searchByCategoryId(categoryId);
            return ResponseEntity.ok(products);
        }


        @GetMapping("/price")
        public ResponseEntity<List<ProductModel>> searchByPriceRange(
                @RequestParam Double minPrice,
                @RequestParam Double maxPrice) {
            List<ProductModel> products = searchService.searchByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(products);
        }



        @PostMapping("/attributes")
        public ResponseEntity<List<ProductModel>> searchWithAttributes(
                @RequestParam String categoryId,
                @RequestBody Map<String, Object> filters) {
            List<ProductModel> products = searchService.searchWithAttributes(categoryId, filters);
            return ResponseEntity.ok(products);
        }


        @PostMapping("/advanced")
        public ResponseEntity<List<ProductModel>> advancedSearch(@RequestBody AdvancedSearchRequest request) {
            List<ProductModel> products = searchService.advancedSearch(
                    request.getId(),
                    request.getBrands(),
                    request.getMaxPrice(),
                    request.getMinStock()
            );
            return ResponseEntity.ok(products);
        }



        @GetMapping("/count-by-category")
        public ResponseEntity<List<CategoryCount>> getProductCountByCategory() {
            List<CategoryCount> counts = searchService.getProductCountByCategory();
            return ResponseEntity.ok(counts);
        }


        @GetMapping("/brand-statistics")
        public ResponseEntity<List<BrandStatistics>> getBrandStatistics() {
            List<BrandStatistics> statistics = searchService.getBrandStatistics();
            return ResponseEntity.ok(statistics);
        }

        @Data
        public static class AdvancedSearchRequest {
            private String id;
            private List<String> brands;
            private Double maxPrice;
            private Integer minStock;

        }

    }
