package com.example.productservice.Repository;


import com.example.productservice.Entity.ProductEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Optional<ProductEntity> findProductByName(String name);
    @Query("{ 'brand': { $regex: ?0, $options: 'i' } }")
    Optional<ProductEntity> findProductByBrand(String brand);
    @Query("{ 'categoryName': { $regex: ?0, $options: 'i' } }")
    List<ProductEntity> findByCategoryName(String categoryName);
}
