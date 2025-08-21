package com.innovator.journal.service;

import com.innovator.journal.ApplicationCache.Cache;
import com.innovator.journal.PojoForApiResponse.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class QuoteService {
    @Autowired
    //this let us integrate external api's it process the request and brings response to us
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Value("${quotes.api.key}")
    private String apiKey;

    @Autowired
    private Cache cache;

    public Quote fetchQuote(){
        Quote quoteOfTheDay = redisService.getDataFromRedis("quote_of_the_day",Quote.class);
        if(quoteOfTheDay !=null ){
            return quoteOfTheDay;
        }
        else{
            HttpHeaders header=new HttpHeaders();
            header.set("X-Api-Key",apiKey);
            header.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> requestEntity=new HttpEntity<>(header);
            ResponseEntity<Quote[]> responseEntity = restTemplate.exchange(
                    cache.getCachedData().get("quote_api"),
                    HttpMethod.GET,
                    requestEntity,
                    Quote[].class
            );
            Quote[] quotes = responseEntity.getBody();
            if (quotes != null && quotes.length > 0) {
                redisService.setDataInRedis("quote_of_the_day",quotes[0],1L);
                return quotes[0];
            }
            return null;
        }
    }
}
