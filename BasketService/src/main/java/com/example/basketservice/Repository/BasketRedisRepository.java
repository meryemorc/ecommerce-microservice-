package com.example.basketservice.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.example.basketservice.Model.BasketModel;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class BasketRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "basket:"; //her sepetin ön ekinde userId olacak basket:1 basket:2 gibi

    public void save(BasketModel basketModel){
        String key = KEY_PREFIX + basketModel.getUserId();
        redisTemplate.opsForValue().set(key, basketModel,24, TimeUnit.HOURS);
    }
    public BasketModel findByUserId(Long userId){
        String key = KEY_PREFIX + userId;
        return  (BasketModel) redisTemplate.opsForValue().get(key);
    }
    public void deleteByUserId(Long userId){
        redisTemplate.delete(KEY_PREFIX + userId);
    }






}
