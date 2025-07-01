package com.tab.enote_app.controller_impl;

import com.tab.enote_app.controller.CacheController;
import com.tab.enote_app.service.CacheManagerService;
import com.tab.enote_app.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class CacheControllerImpl implements CacheController {

    private final CacheManagerService cacheManagerService;

    @Override
    public ResponseEntity<?> getAllCache(){
        Collection<String> allCache = cacheManagerService.getAllCache();
        return CommonUtil.createResponse(allCache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCache(String cacheName){
        Cache cache = cacheManagerService.getCache(cacheName);
        return CommonUtil.createResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeAllCache(){
        cacheManagerService.removeAllCache();
        return CommonUtil.createResponse("all cache removed", HttpStatus.OK);
    }
}
