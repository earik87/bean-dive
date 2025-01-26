package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.RoasteryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScraperScheduler {

    private final ScraperFacade scraperFacade;
    private final ScrapedDataCache scrapedDataCache;

    @Scheduled(initialDelay = 2000, fixedDelay = Long.MAX_VALUE)
    public void initialScrape() {
        if (scrapedDataCache.isEmpty()) {
            log.info("Cache is empty, performing initial scrape");
            scrapeData();
        } else {
            log.info("Cache contains {} beans", scrapedDataCache.getData().size());
        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    @SuppressWarnings("unused")
    public void scheduledScrape() {
        log.info("Starting scheduled data scrape at {}", LocalDateTime.now());
        long startTime = System.currentTimeMillis();
        
        scrapeData();
        
        long endTime = System.currentTimeMillis();
        log.info("Completed scheduled data scrape. Duration: {} ms", endTime - startTime);
    }

    public void scrapeData() {
        log.info("Data scraper started...");

        List<Bean> scrapedData = scraperFacade.fetchBeanPrices(RoasteryFactory.getRoasteries());

        scrapedDataCache.updateCache(scrapedData);
        log.info("Cache updated with {} beans", scrapedData.size());
    }
}