package com.innovator.journal.Schedulers;

import com.innovator.journal.ApplicationCache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class CacheReinitializeScheduler {

    @Autowired
    private Cache cache;

    @Scheduled(cron = "0 */5 * * * *")
    public void reInitializeAppCache(){
        cache.cacheInitializer();
    }
}
