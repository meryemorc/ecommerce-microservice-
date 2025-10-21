package com.example.productservice.Service;

import com.example.productservice.Dto.SearchDto;
import com.example.productservice.Model.SearchModel;
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

    private List<SearchDto> convertToDto(List<SearchModel> searchModelList) {
        return searchModelList.stream()
                .map(product -> modelMapper.map(product, SearchDto.class))
                .collect(Collectors.toList());
    }

    public List<SearchDto> searchByName(String name) {
        List<SearchModel> products = searchRepository.findByNameContaining(name);
        return convertToDto(products);
    }

    public List<SearchDto> searchByBrand(String brand) {
        List<SearchModel> products = searchRepository.findByBrand(brand);
        products.stream().filter(p -> p.getColor().equalsIgnoreCase("siyah")).collect(Collectors.toList());
        return convertToDto(products);
    }

    public List<SearchDto> searchByBrandAndCategory(String brand, String categoryName) {
        List<SearchModel> products = searchRepository.findByBrandAndCategoryName(brand, categoryName);
        return convertToDto(products);
    }
    public List<SearchDto> searchByPriceBetween(Double minPrice, Double maxPrice) {
        List<SearchModel> products = searchRepository.findByPriceBetween(minPrice, maxPrice);
        return convertToDto(products);
    }
    public List<SearchDto> searchByBrandAndPriceBetween(String brand,Double minPrice, Double maxPrice) {
        List<SearchModel> products = searchRepository.findByBrandAndPriceBetween(brand,minPrice,maxPrice);
        return convertToDto(products);
    }
    public List<SearchDto> searchByColor(String color) {
        List<SearchModel> products = searchRepository.findByColor(color);
        return convertToDto(products);
    }
    public List<SearchDto> searchByNameContainingOrderByPriceAsc(String categoryName) {
        List<SearchModel> products = searchRepository.findByCategoryNameContainingOrderByPriceAsc(categoryName);
        return convertToDto(products);
    }
    public List<SearchDto> findByNameContainingOrderByPriceDesc(String categoryName) {
        List<SearchModel> products = searchRepository.findByCategoryNameContainingOrderByPriceDesc(categoryName);
        return convertToDto(products);
    }

}
