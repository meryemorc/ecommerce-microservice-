package com.example.productservice.Controller;

import com.example.productservice.Dto.SearchDto;
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
    public ResponseEntity<List<SearchDto>> searchByName(@RequestParam String name) {
        List<SearchDto> products = searchService.searchByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/brand")
    public ResponseEntity<List<SearchDto>> searchByBrand(@RequestParam String brand) {
        List<SearchDto> products = searchService.searchByBrand(brand);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category-brand")
    public ResponseEntity<List<SearchDto>> searchByBrandAndCategory(@RequestParam String brand,
                                                                            @RequestParam String categoryName) {
        List<SearchDto> products = searchService.searchByBrandAndCategory(brand, categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/priceFilter")
    public ResponseEntity<List<SearchDto>> searchByPriceFilter(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {

        List<SearchDto> products = searchService.searchByPriceBetween(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/brand-price")
    public ResponseEntity<List<SearchDto>> searchByBrandAndPrice(
            @RequestParam String brand,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<SearchDto> results = searchService.searchByBrandAndPriceBetween(brand, minPrice, maxPrice);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/color")
    public ResponseEntity<List<SearchDto>> searchByColor(@RequestParam String color) {
        List<SearchDto> results = searchService.searchByColor(color);
        return ResponseEntity.ok(results);
    }

    // ucuzdan pahalıya
    @GetMapping("/name/sort-asc")
    public ResponseEntity<List<SearchDto>> searchByNameSortByPriceAsc(@RequestParam String categoryName) {
        List<SearchDto> results = searchService.searchByNameContainingOrderByPriceAsc(categoryName);
        return ResponseEntity.ok(results);
    }

    // pahalıdan ucuza
    @GetMapping("/name/sort-desc")
    public ResponseEntity<List<SearchDto>> searchByNameSortByPriceDesc(@RequestParam String categoryName) {
        List<SearchDto> results = searchService.findByNameContainingOrderByPriceDesc(categoryName);
        return ResponseEntity.ok(results);
    }
}
