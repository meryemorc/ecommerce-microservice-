package com.example.productservice.Controller;

import com.example.productservice.Dto.Response.SearchResponseDto;
import com.example.productservice.Service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/name")
    public ResponseEntity<List<SearchResponseDto>> searchByName(@RequestParam String name) {
        List<SearchResponseDto> products = searchService.searchByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/brand")
    public ResponseEntity<List<SearchResponseDto>> searchByBrand(@RequestParam String brand) {
        List<SearchResponseDto> products = searchService.searchByBrand(brand);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category-brand")
    public ResponseEntity<List<SearchResponseDto>> searchByBrandAndCategory(@RequestParam String brand,
                                                                            @RequestParam String categoryName) {
        List<SearchResponseDto> products = searchService.searchByBrandAndCategory(brand, categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/priceFilter")
    public ResponseEntity<List<SearchResponseDto>> searchByPriceFilter(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {

        List<SearchResponseDto> products = searchService.searchByPriceBetween(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/brand-price")
    public ResponseEntity<List<SearchResponseDto>> searchByBrandAndPrice(
            @RequestParam String brand,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<SearchResponseDto> results = searchService.searchByBrandAndPriceBetween(brand, minPrice, maxPrice);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/color")
    public ResponseEntity<List<SearchResponseDto>> searchByColor(@RequestParam String color) {
        List<SearchResponseDto> results = searchService.searchByColor(color);
        return ResponseEntity.ok(results);
    }

    // ucuzdan pahalıya
    @GetMapping("/name/sort-asc")
    public ResponseEntity<List<SearchResponseDto>> searchByNameSortByPriceAsc(@RequestParam String categoryName) {
        List<SearchResponseDto> results = searchService.searchByNameContainingOrderByPriceAsc(categoryName);
        return ResponseEntity.ok(results);
    }

    // pahalıdan ucuza
    @GetMapping("/name/sort-desc")
    public ResponseEntity<List<SearchResponseDto>> searchByNameSortByPriceDesc(@RequestParam String categoryName) {
        List<SearchResponseDto> results = searchService.findByNameContainingOrderByPriceDesc(categoryName);
        return ResponseEntity.ok(results);
    }
}
