package com.example.productservice.Service;

import com.example.productservice.Dto.Response.SearchResponseDto;
import com.example.productservice.Entity.SearchEntity;
import com.example.productservice.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final ModelMapper modelMapper;

    private List<SearchResponseDto> convertToDto(List<SearchEntity> searchEntityList) {
        return searchEntityList.stream()
                .map(product -> modelMapper.map(product, SearchResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<SearchResponseDto> searchByName(String name) {
        List<SearchEntity> products = searchRepository.findByNameContaining(name);
        return convertToDto(products);
    }

    public List<SearchResponseDto> searchByBrand(String brand) {
        List<SearchEntity> products = searchRepository.findByBrand(brand);
        return convertToDto(products);
    }

    public List<SearchResponseDto> searchByBrandAndCategory(String brand, String categoryName) {
        List<SearchEntity> products = searchRepository.findByBrandAndCategoryName(brand, categoryName);
        return convertToDto(products);
    }
    public List<SearchResponseDto> searchByPriceBetween(Double minPrice, Double maxPrice) {
        List<SearchEntity> products = searchRepository.findByPriceBetween(minPrice, maxPrice);
        return convertToDto(products);
    }
    public List<SearchResponseDto> searchByBrandAndPriceBetween(String brand,Double minPrice, Double maxPrice) {
        List<SearchEntity> products = searchRepository.findByBrandAndPriceBetween(brand,minPrice,maxPrice);
        return convertToDto(products);
    }
    public List<SearchResponseDto> searchByColor(String color) {
        List<SearchEntity> products = searchRepository.findByColor(color);
        return convertToDto(products);
    }
    public List<SearchResponseDto> searchByNameContainingOrderByPriceAsc(String categoryName) {
        List<SearchEntity> products = searchRepository.findByCategoryNameContainingOrderByPriceAsc(categoryName);
        return convertToDto(products);
    }
    public List<SearchResponseDto> findByNameContainingOrderByPriceDesc(String categoryName) {
        List<SearchEntity> products = searchRepository.findByCategoryNameContainingOrderByPriceDesc(categoryName);
        return convertToDto(products);
    }

}
