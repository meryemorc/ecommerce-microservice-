package com.example.productservice.Service;

import com.example.productservice.Dto.CategoryDto;
import com.example.productservice.Model.CategoryModel;
import com.example.productservice.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDto> findAll(){
        List<CategoryModel> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryModel -> modelMapper.map(categoryModel, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
