package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.services.scraperstrategies.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScraperFacade {

    private final HTMLCoffeeBeanScraper htmlCoffeeBeanScraper;
    private final JsonLDCoffeeBeanScraper jsonLDCoffeeBeanScraper;
    private final InlineJsonCoffeeBeanScraper inlineJsonCoffeeBeanScraper;
    private final PartialClassMatchCoffeeBeanScraper partialClassMatchCoffeeBeanScraper;
    private final ProductCardCoffeeBeanScraper productCardCoffeeBeanScraper;
    private final JsonLDAndHTMLCoffeeBeanScraper jsonLDAndHTMLCoffeeBeanScraper;
    private final SelectorBasedCoffeeBeanScraper selectorBasedCoffeeBeanScraper;

    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_BACKOFF_MS = 1000;
    private static final long MAX_BACKOFF_MS = 10000;
    private static final Random random = new Random();

    public List<Bean> fetchBeanPrices(List<Roastery> roasteries) {
        List<Bean> beans = new ArrayList<>();
        List<String> successfulRoasteries = new ArrayList<>();
        List<String> failedRoasteries = new ArrayList<>();

        log.info("Starting to fetch bean prices from {} roasteries", roasteries.size());

        for (Roastery roastery : roasteries) {
            List<Bean> roasteryBeans = fetchWithRetry(roastery);
            if (!roasteryBeans.isEmpty()) {
                beans.addAll(roasteryBeans);
                successfulRoasteries.add(roastery.getName());
            } else {
                failedRoasteries.add(roastery.getName());
            }
        }

        log.info("Successfully fetched beans from {} roasteries: {}", 
                 successfulRoasteries.size(), String.join(", ", successfulRoasteries));
        if (!failedRoasteries.isEmpty()) {
            log.warn("Failed to fetch beans from {} roasteries: {}", 
                     failedRoasteries.size(), String.join(", ", failedRoasteries));
        }

        List<String> excludedWords = List.of("kitap", "Aboneliği", "kağıdı", "Nespresso");

        List<Bean> filteredBeans = beans.stream()
                .filter(bean -> bean.getPrice() >= 100)
                .filter(bean -> excludedWords.stream().noneMatch(word -> bean.getName().toLowerCase().contains(word.toLowerCase())))
                .sorted(Comparator.comparingInt(Bean::getPrice))
                .collect(Collectors.toList());

        log.info("Fetched a total of {} beans, {} after filtering", beans.size(), filteredBeans.size());

        return filteredBeans;
    }

    private List<Bean> fetchWithRetry(Roastery roastery) {
        long backoffMs = INITIAL_BACKOFF_MS;
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                List<Bean> fetchedBeans = fetchFromRoastery(roastery);
                if (!fetchedBeans.isEmpty()) {
                    return fetchedBeans;
                }
                log.warn("Attempt {} for {} returned no beans", attempt + 1, roastery.getName());
            } catch (HttpStatusException e) {
                if (attempt < MAX_RETRIES - 1) {
                    try {
                        Thread.sleep(backoffMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                    backoffMs = Math.min(backoffMs * 2 + random.nextInt(1000), MAX_BACKOFF_MS);
                }
            } catch (Exception e) {
                log.error("Unexpected error occurred while fetching beans from {}: {}", roastery.getName(), e.getMessage());
                return new ArrayList<>(); // Return empty list for non-HTTP related errors
            }
            
        }
        log.error("Failed to fetch bean prices from {} after {} attempts", roastery.getName(), MAX_RETRIES);
        return new ArrayList<>();
    }

    private List<Bean> fetchFromRoastery(Roastery roastery) throws Exception {
        switch (roastery.getScrapeType()) {
            case HTML:
                return htmlCoffeeBeanScraper.fetchBeanPrices(roastery);
            case JSON_LD:
                return jsonLDCoffeeBeanScraper.fetchBeanPrices(roastery);
            case INLINE_JSON:
                return inlineJsonCoffeeBeanScraper.fetchBeanPrices(roastery);
            case PARTIAL_CLASS_MATCH:
                return partialClassMatchCoffeeBeanScraper.fetchBeanPrices(roastery);
            case PRODUCT_CARD:
                return productCardCoffeeBeanScraper.fetchBeanPrices(roastery);
            case JSON_LD_AND_HTML:
                return jsonLDAndHTMLCoffeeBeanScraper.fetchBeanPrices(roastery);
            case SELECTOR_BASED:
                return selectorBasedCoffeeBeanScraper.fetchBeanPrices(roastery);
            default:
                throw new IllegalArgumentException("Unsupported scrape type: " + roastery.getScrapeType());
        }
    }
}
