package com.example.productservice.Service;

import com.example.productservice.Dto.CategoryDto;
import com.example.productservice.Entity.CategoryEntity;
import com.example.productservice.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto> findAll(){
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
