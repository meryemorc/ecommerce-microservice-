package com.example.productservice.Repository;


import com.example.productservice.Entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

}
