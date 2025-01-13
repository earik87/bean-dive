package com.ocean_roast.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean_roast.models.Bean;
import com.ocean_roast.models.RoasteryFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScraperScheduler {
    private final ScraperFacade scraperFacade;
    private final ObjectMapper objectMapper;
    private final ScrapedDataCache scrapedDataCache;

    private static final String DATA_DIR = "/data";

    // ✅ Runs once immediately after startup
    @PostConstruct
    public void init() {
        log.info("Running initial data scrape at startup.");
        scrapeData();
    }

    // 🕒 Runs every day at 2:00 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void scrapeData() {
        log.info("Scheduled data scraper started...");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        // Scrape data
        List<Bean> scrapedData = scraperFacade.fetchBeanPrices(RoasteryFactory.getRoasteries());

        // Define file path
        String fileName = "coffee_data_" + timestamp + ".json";
        String filePath = Paths.get(DATA_DIR, fileName).toString();

        try {
            // Save scraped data to file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), scrapedData);
            log.info("Data saved to: {}", filePath);

            // ✅ Update cache after saving
            scrapedDataCache.updateCache(scrapedData);
            log.info("Cache updated with latest scraped data.");
        } catch (IOException e) {
            log.error("Failed to save scraped data: {}", e.getMessage());
        }
    }
}
