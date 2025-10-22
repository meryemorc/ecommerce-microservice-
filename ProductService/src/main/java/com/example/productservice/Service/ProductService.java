package com.example.productservice.Service;

import com.example.productservice.Dto.ProductDto;
import com.example.productservice.Model.ProductModel;
import com.example.productservice.Model.SearchModel;
import com.example.productservice.Repository.ProductRepository;
import com.example.productservice.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SearchRepository searchRepository;
    private final ModelMapper modelMapper;


    public ProductDto getProductById(String id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("ürün bulunamadı");
        }
        ProductModel entity = product.get();
        ProductDto responseDto = new ProductDto();

        responseDto.setName(entity.getName());
        responseDto.setCategoryId(entity.getCategoryId());
        responseDto.setBrand(entity.getBrand());
        responseDto.setPrice(entity.getPrice());
        responseDto.setDescription(entity.getDescription());
        responseDto.setColor(entity.getColor());
        responseDto.setStock(entity.getStock());

        return responseDto;
    }

    public ProductDto getProductByName(String name) {
        Optional<ProductModel> product = productRepository.findProductByName(name);
        if (product.isEmpty()) {
            throw new RuntimeException("ürün bulunamadı");
        }
        ProductModel entity = product.get();
        return modelMapper.map(entity, ProductDto.class);
    }

    public ProductDto getProductByBrand(String brand) {
        String normalized = brand.trim().toLowerCase();
        Optional<ProductModel> product = productRepository.findProductByBrand(brand);
        if (product.isEmpty()) {
            throw new RuntimeException("ürün bulunamadı");
        }
        ProductModel entity = product.get();
        return modelMapper.map(entity, ProductDto.class);
    }

    public List<ProductDto> getProductCategoryName(String categoryName) {
        categoryName = categoryName.trim();
        List<ProductModel> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(productModel -> modelMapper.map(productModel, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        return products.stream()
                .map(productModel -> modelMapper.map(productModel, ProductDto.class))
                .collect(Collectors.toList());
    }

    public void syncAllProductsToElasticsearch() {
        List<ProductModel> allProducts = productRepository.findAll();
        for (ProductModel products : allProducts) {
            SearchModel search = convertToSearchDoc(products);
            searchRepository.save(search);
        }
    }

    private SearchModel convertToSearchDoc(ProductModel product) {
        SearchModel search = modelMapper.map(product, SearchModel.class);
        search.setId(product.getId().toString());
        return search;
    }

    public void decreaseStock(String productId, Integer quantity) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productId));

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

    }

    public boolean checkCtock(String productId, Integer quantity) {
        try {
            ProductModel product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("ürün bulunamadı "));
            boolean hasStock = product.getStock() >= quantity;
            return hasStock;
        } catch (Exception e) {
            log.error("Stok kontrolu yapılamadı: {}", e.getMessage());
            return false;
        }
    }
}





