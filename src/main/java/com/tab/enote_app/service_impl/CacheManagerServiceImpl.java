package com.tab.enote_app.service_impl;

import com.tab.enote_app.service.CacheManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheManagerServiceImpl implements CacheManagerService {

    private final CacheManager cacheManager;

    public Collection<String> getAllCache(){

        Collection<String> cacheNames = cacheManager.getCacheNames();

        for(String cacheName : cacheNames){
            Cache cache = cacheManager.getCache(cacheName);
            log.info("Cache : " + cache);
        }

        log.info("All cache : " + cacheNames);
        return cacheNames;
    }

    public Cache getCache(String cacheName){

        Cache cache = cacheManager.getCache(cacheName);
        log.info("Cache : " + cache);
        return cache;
    }

    public void removeAllCache(){

        Collection<String> cacheNames = cacheManager.getCacheNames();

        for(String cacheName : cacheNames){
            Cache cache = cacheManager.getCache(cacheName);
            log.info("Cache : " + cache);
            cache.clear();
        }

    }

    @Override
    public void removeCacheByName(List<String> cacheNames) {
        for(String cacheName : cacheNames){
            Cache cache = cacheManager.getCache(cacheName);
            log.info("Cache : " + cache);
            cache.clear();
        }
    }


}
