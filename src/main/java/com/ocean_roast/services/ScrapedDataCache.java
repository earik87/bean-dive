package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ScrapedDataCache {
    private final AtomicReference<List<Bean>> cache = new AtomicReference<>(new ArrayList<>());

    public List<Bean> getData() {
        return new ArrayList<>(cache.get());
    }

    public void updateCache(List<Bean> newData) {
        cache.set(new ArrayList<>(newData));
    }

    public boolean isEmpty() {
        return cache.get().isEmpty();
    }
}