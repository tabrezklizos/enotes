package com.tab.enote_app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name="cache",description = "cache APIs")
@RequestMapping("/api/v1/cache")
public interface CacheController {

    @GetMapping("/")
    public ResponseEntity<?> getAllCache();

    @GetMapping("/{cacheName}")
    public ResponseEntity<?> getCache(@PathVariable String cacheName);

    @DeleteMapping("/")
    public ResponseEntity<?> removeAllCache();

}
