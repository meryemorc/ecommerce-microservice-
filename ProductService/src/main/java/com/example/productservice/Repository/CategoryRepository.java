package com.example.productservice.Repository;

import com.example.productservice.Dto.Response.CategoryResponseDto;
import com.example.productservice.Entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {

}
