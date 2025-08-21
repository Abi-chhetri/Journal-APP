package com.innovator.journal.ApplicationCache;

import com.innovator.journal.Entity.AppCacheConfig;
import com.innovator.journal.repository.AppCacheConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class Cache {
    @Autowired
    private AppCacheConfigRepository appCacheConfigRepository;

    private Map<String, String> cachedData;

    @PostConstruct
    public void cacheInitializer(){
        cachedData=new HashMap<>();
        List<AppCacheConfig>cache=appCacheConfigRepository.findAll();
        for(AppCacheConfig all: cache){
            cachedData.put(all.getKey(),all.getValue());
        }
    }
}
