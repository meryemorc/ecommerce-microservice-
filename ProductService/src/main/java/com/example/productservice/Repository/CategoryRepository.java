package com.example.productservice.Repository;


import com.example.productservice.Model.CategoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface CategoryRepository extends MongoRepository<CategoryModel, String> {

}
