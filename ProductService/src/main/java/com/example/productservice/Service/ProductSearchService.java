package com.example.productservice.Service;

import com.example.productservice.Entity.ProductEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ProductSearchService {
    private final MongoTemplate mongoTemplate;

    public List<ProductEntity> searchByCategoryId(String categoryId){

        Criteria criteria = Criteria.where("categoryId").is(categoryId);
        Query query = new Query(criteria);
        return mongoTemplate.find(query , ProductEntity.class);
    }

    public List<ProductEntity> searchByPriceRange(Double minPrice, Double maxPrice){
        Criteria criteria = Criteria.where("price").gte(minPrice).lte(maxPrice);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, ProductEntity.class);
    }
    public List<CategoryCount> getProductCountByCategory() {
        // 1. Group operation: categoryId'ye göre grupla ve say
        GroupOperation groupOperation = Aggregation.group("categoryId")
                .count().as("count");

        // 2. Aggregation pipeline oluştur
        Aggregation aggregation = Aggregation.newAggregation(groupOperation);

        // 3. Çalıştır
        AggregationResults<CategoryCount> results =
                mongoTemplate.aggregate(aggregation, "products", CategoryCount.class);

        return results.getMappedResults();
    }
    @Data
    public class CategoryCount{
        private String _id;
        private Long count;
    }

    @Data
    public static class BrandStatistics {
        private String _id;           // brand (marka adı)
        private Double avgPrice;      // ortalama fiyat
        private Long productCount;    // ürün sayısı
    }
    public List<BrandStatistics> getBrandStatistics() {
        GroupOperation groupOperation = Aggregation.group("brand")
                .avg("price").as("avgPrice")
                .count().as("productCount");

        Aggregation aggregation = Aggregation.newAggregation(groupOperation);

        AggregationResults<BrandStatistics> results =
                mongoTemplate.aggregate(aggregation, "products", BrandStatistics.class);

        return results.getMappedResults();
    }
    public List<ProductEntity> advancedSearch(String categoryId,List<String> brand, Double maxPrice, Integer minStock){
        Criteria criteria = new Criteria();

        if(categoryId != null){
            criteria.and("categoryId").is(categoryId);
        }
        if(brand != null && !brand.isEmpty()){
            criteria.and("brand").in(brand);
        }
        if(maxPrice != null){
            criteria.and("price").lte(maxPrice);
        }
        if(minStock != null){
            criteria.and("stock").gte(minStock);
        }
        Query query = new Query(criteria);
        return mongoTemplate.find(query, ProductEntity.class);
    }
    public List<ProductEntity> searchWithAttributes(String categoryId, Map<String,Object> filters){
        Criteria criteria = Criteria.where("categoryId").is(categoryId);

        // filters Map'indeki her bir key-value tek tek al
        for (Map.Entry<String, Object> entry : filters.entrySet()) {

            // MongoDB'de nested(iç içe geçmişalan) field'a erişim için "attributes." ön ekini ekle
            String key = "attributes." + entry.getKey();

            // Criteria'ya yeni bir koşul ekle: attributes.ram = "8GB"
            // .and(key) → yeni koşul ekle
            // .is(value) → eşitlik kontrolü (=)
            criteria.and(key).is(entry.getValue());
        }
        Query query =new Query(criteria);
        return mongoTemplate.find(query, ProductEntity.class);
    }
}

