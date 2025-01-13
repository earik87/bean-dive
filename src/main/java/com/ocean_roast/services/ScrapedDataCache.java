package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ScrapedDataCache {
    private final AtomicReference<List<Bean>> cachedBeans = new AtomicReference<>();

    // Update the cache with fresh data
    public void updateCache(List<Bean> beans) {
        cachedBeans.set(beans);
        System.out.println("Cache updated with " + beans.size() + " items.");
    }

    // Retrieve cached data
    public List<Bean> getCachedBeans() {
        return cachedBeans.get();
    }
}
