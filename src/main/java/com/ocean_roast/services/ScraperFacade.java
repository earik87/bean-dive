package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScraperFacade {

    private final HTMLCoffeeBeanScraper htmlCoffeeBeanScraper;
    private final JsonLDCoffeeBeanScraper jsonLDCoffeeBeanScraper;
    private final InlineJsonCoffeeBeanScraper inlineJsonCoffeeBeanScraper;

    public List<Bean> fetchBeanPrices(List<Roastery> roasteries) {
        List<Bean> beans = new ArrayList<>();
        for (Roastery roastery : roasteries) {
            switch (roastery.getScrapeType()) {
                case HTML:
                    beans.addAll(htmlCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices with htmlCoffeeBeanScraper.");
                    break;
                case JSON_LD:
                    beans.addAll(jsonLDCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices with jsonLDCoffeeBeanScraper.");
                    break;
                case INLINE_JSON:
                    beans.addAll(inlineJsonCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices with inlineJsonCoffeeBeanScraper.");
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported scrape type: " + roastery.getScrapeType());
            }
        }
        beans.sort(Comparator.comparingInt(Bean::getPrice));
        return beans;
    }
}
