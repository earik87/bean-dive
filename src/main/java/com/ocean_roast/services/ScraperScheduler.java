package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class ScraperScheduler {
    private final ScraperFacade scraperFacade;
    private final ScrapedDataCache scrapedDataCache;

    // Runs once, 10 seconds after startup
    @Scheduled(initialDelay = 10000, fixedDelay = Long.MAX_VALUE)
    public void initialScrape() {
        log.info("Running initial data scrape 10 seconds after startup.");
        scrapeData();
    }

    // Runs daily at 2 AM
    @Scheduled(cron = "0 0 2 * * *")
    public void scheduledScrape() {
        log.info("Running scheduled data scrape at 2 AM.");
        scrapeData();
    }

    // 🕒 Runs every day at 2:00 AM
    public void scrapeData() {
        log.info("Scheduled data scraper started...");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        // Scrape data
        List<Bean> scrapedData = scraperFacade.fetchBeanPrices(RoasteryFactory.getRoasteries());

        // ✅ Update cache after saving
        scrapedDataCache.updateCache(scrapedData);
        log.info("Cache updated with latest scraped data.");
    }
}