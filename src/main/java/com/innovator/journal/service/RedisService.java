package com.innovator.journal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovator.journal.PojoForApiResponse.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    public <T> T getDataFromRedis(String key, Class<T> entityClass){
        try {
            Object quote= redisTemplate.opsForValue().get(key);
            if(quote !=null){
                ObjectMapper mapper=new ObjectMapper();
                return mapper.readValue(quote.toString(), entityClass);

            }
        } catch (NullPointerException | JsonProcessingException e) {
            log.error("Error while converting data into Json, {}", String.valueOf(e));
        }
        return null;
    }

    public void setDataInRedis(String key, Quote quote, Long expiryTime){
        try{
            ObjectMapper mapper=new ObjectMapper();
            String quoteInJson=mapper.writeValueAsString(quote);
            redisTemplate.opsForValue().set(key, quoteInJson, expiryTime, TimeUnit.DAYS);
        } catch (Exception e) {
            log.error("There was an error while saving data in redis, {} {}",e.getCause(), e.getMessage());
            throw new RuntimeException();
        }
    }

}
