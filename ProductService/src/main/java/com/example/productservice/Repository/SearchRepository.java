package com.example.productservice.Repository;

import com.example.productservice.Model.SearchModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchModel, String> {
     List<SearchModel> findByNameContaining(String name);
     List<SearchModel> findByBrand(String brand);
     List<SearchModel> findByBrandAndCategoryName(String brand, String categoryName);
     List<SearchModel> findByPriceBetween(Double minPrice, Double maxPrice);
     List<SearchModel> findByBrandAndPriceBetween(String brand, Double minPrice, Double maxPrice);
     List<SearchModel> findByColor(String color);
     List<SearchModel> findByCategoryNameContainingOrderByPriceAsc(String categoryName);// ucuzdan pahalıya
     List<SearchModel> findByCategoryNameContainingOrderByPriceDesc(String categoryName);//pahalıdan ucuza

}
