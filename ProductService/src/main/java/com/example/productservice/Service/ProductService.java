package com.example.productservice.Service;

import com.example.productservice.Dto.ProductDto;
import com.example.productservice.Entity.ProductEntity;
import com.example.productservice.Entity.SearchEntity;
import com.example.productservice.Repository.ProductRepository;
import com.example.productservice.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SearchRepository searchRepository;
    private final ModelMapper modelMapper;


    public ProductDto getProductById(ObjectId id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("ürün bulunamadı");
        }
        ProductEntity entity = product.get();
        ProductDto responseDto = new ProductDto();

        responseDto.setName(entity.getName());
        responseDto.setCategoryId(entity.getCategoryId());
        responseDto.setBrand(entity.getBrand());
        responseDto.setPrice(entity.getPrice());
        responseDto.setDescription(entity.getDescription());
        responseDto.setColor(entity.getColor());


        return responseDto;
    }

    public ProductDto getProductByName(String name) {
    Optional<ProductEntity> product =productRepository.findProductByName(name);
    if(product.isEmpty()){
        throw new RuntimeException("ürün bulunamadı");
    }
    ProductEntity entity = product.get();
    return modelMapper.map(entity, ProductDto.class);
    }

    public ProductDto getProductByBrand(String brand) {
        String normalized = brand.trim().toLowerCase();
        Optional<ProductEntity> product = productRepository.findProductByBrand(brand);
        if(product.isEmpty()){
            throw new RuntimeException("ürün bulunamadı");
        }
        ProductEntity entity=product.get();
        return modelMapper.map(entity, ProductDto.class);
    }

    public List<ProductDto> getProductCategoryName(String categoryName) {
        categoryName = categoryName.trim();
        List<ProductEntity> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    public void syncAllProductsToElasticsearch(){
        List<ProductEntity> allProducts =productRepository.findAll();
        for(ProductEntity products : allProducts){
            SearchEntity search = convertToSearchDoc(products);
            searchRepository.save(search);
        }
    }

    private SearchEntity convertToSearchDoc(ProductEntity product){
        SearchEntity search = modelMapper.map(product, SearchEntity.class);
        search.setId(product.getId().toString());
        return search;
    }

}





