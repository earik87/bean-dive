package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.RoasteryFactory;
import com.ocean_roast.services.scrapedDataService.BeanPriceDataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class ScraperScheduler {

    private final ScraperFacade scraperFacade;
    private final ScrapedDataCache scrapedDataCache;
    private final BeanPriceDataService beanPriceDataService;

    @PostConstruct
    public void initializeCache() {
        List<Bean> savedData = beanPriceDataService.loadData();
        scrapedDataCache.updateCache(savedData);
        log.info("Cache initialized with {} beans from saved data.", savedData.size());
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void scheduledScrape() {
        log.info("Running scheduled data scrape at 2 AM.");
        scrapeData();
    }

    public void scrapeData() {
        log.info("Scheduled data scraper started...");

        List<Bean> scrapedData = scraperFacade.fetchBeanPrices(RoasteryFactory.getRoasteries());

        scrapedDataCache.updateCache(scrapedData);
        beanPriceDataService.saveData(scrapedData);
        log.info("Cache and persistent storage updated with latest scraped data.");
    }
}