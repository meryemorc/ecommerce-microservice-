package com.example.productservice.Repository;


import com.example.productservice.Model.ProductModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductModel, String> {

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Optional<ProductModel> findProductByName(String name);
    @Query("{ 'brand': { $regex: ?0, $options: 'i' } }")
    Optional<ProductModel> findProductByBrand(String brand);
    @Query("{ 'categoryName': { $regex: ?0, $options: 'i' } }")
    List<ProductModel> findByCategoryName(String categoryName);
}
