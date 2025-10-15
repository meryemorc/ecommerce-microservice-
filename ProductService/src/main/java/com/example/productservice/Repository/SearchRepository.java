package com.example.productservice.Repository;

import com.example.productservice.Entity.SearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchEntity, String> {
     List<SearchEntity> findByNameContaining(String name);
     List<SearchEntity> findByBrand(String brand);
     List<SearchEntity> findByBrandAndCategoryName(String brand, String categoryName);
     List<SearchEntity> findByPriceBetween(Double minPrice, Double maxPrice);
     List<SearchEntity> findByBrandAndPriceBetween(String brand, Double minPrice, Double maxPrice);
     List<SearchEntity> findByColor(String color);
     List<SearchEntity> findByCategoryNameContainingOrderByPriceAsc(String categoryName);// ucuzdan pahalıya
     List<SearchEntity> findByCategoryNameContainingOrderByPriceDesc(String categoryName);//pahalıdan ucuza

}
