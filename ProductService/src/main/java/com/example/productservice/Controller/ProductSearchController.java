package com.example.productservice.Controller;

import com.example.productservice.Entity.ProductEntity;
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

        // ========== CRİTERİA ENDPOİNTLERİ ==========

        /**
         * 1. Kategoriye göre ürün ara
         * GET /api/products/search/category/{categoryId}
         * Örnek: /api/products/search/category/cat-elektronik-telefon
         */
        @GetMapping("/category/{categoryId}")
        public ResponseEntity<List<ProductEntity>> searchByCategory(@PathVariable String categoryId) {
            List<ProductEntity> products = searchService.searchByCategoryId(categoryId);
            return ResponseEntity.ok(products);
        }

        /**
         * 2. Fiyat aralığına göre ara
         * GET /api/products/search/price?minPrice=1000&maxPrice=5000
         */
        @GetMapping("/price")
        public ResponseEntity<List<ProductEntity>> searchByPriceRange(
                @RequestParam Double minPrice,
                @RequestParam Double maxPrice) {
            List<ProductEntity> products = searchService.searchByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(products);
        }

        /**
         * 3. Dinamik attribute filtresi
         * POST /api/products/search/attributes?categoryId=cat-elektronik-telefon
         * Body: {"ram": "8GB", "storage": "256GB"}
         */
        @PostMapping("/attributes")
        public ResponseEntity<List<ProductEntity>> searchWithAttributes(
                @RequestParam String categoryId,
                @RequestBody Map<String, Object> filters) {
            List<ProductEntity> products = searchService.searchWithAttributes(categoryId, filters);
            return ResponseEntity.ok(products);
        }

        /**
         * 4. Gelişmiş arama (çoklu filtre)
         * Body: {"brands": ["Apple", "Samsung"], "maxPrice": 50000, "minStock": 0}
         */
        @PostMapping("/advanced")
        public ResponseEntity<List<ProductEntity>> advancedSearch(@RequestBody AdvancedSearchRequest request) {
            List<ProductEntity> products = searchService.advancedSearch(
                    request.getId(),
                    request.getBrands(),
                    request.getMaxPrice(),
                    request.getMinStock()
            );
            return ResponseEntity.ok(products);
        }

        /**
         * 5. Kategorilere göre ürün sayısı
         * GET /api/products/search/count-by-category
         */
        @GetMapping("/count-by-category")
        public ResponseEntity<List<CategoryCount>> getProductCountByCategory() {
            List<CategoryCount> counts = searchService.getProductCountByCategory();
            return ResponseEntity.ok(counts);
        }

        /**
         * 6. Markalara göre istatistikler (ortalama fiyat, ürün sayısı)
         * GET /api/products/search/brand-statistics
         */
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
