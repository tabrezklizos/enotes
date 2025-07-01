package com.tab.enote_app.service;


import org.springframework.cache.Cache;

import java.util.Collection;
import java.util.List;

public interface CacheManagerService {

    public Collection<String> getAllCache();

    public Cache getCache(String cacheName);

    public void removeAllCache();

    public void removeCacheByName(List<String> cacheNames);



}
