package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public List<Bean> fetchBeanPrices(List<Roastery> roasteries) {
        List<Bean> beans = new ArrayList<>();
        for (Roastery roastery : roasteries) {
            switch (roastery.getScrapeType()) {
                case HTML:
                    beans.addAll(htmlCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                case JSON_LD:
                    beans.addAll(jsonLDCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                case INLINE_JSON:
                    beans.addAll(inlineJsonCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                case PARTIAL_CLASS_MATCH:
                    beans.addAll(partialClassMatchCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                case PRODUCT_CARD:
                    beans.addAll(productCardCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                case JSON_LD_AND_HTML:
                    beans.addAll(jsonLDAndHTMLCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                case SELECTOR_BASED:
                    beans.addAll(selectorBasedCoffeeBeanScraper.fetchBeanPrices(roastery));
                    log.info("Successfully fetched bean prices from: " + roastery.getName());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported scrape type: " + roastery.getScrapeType());
            }
        }

        List<String> excludedWords = List.of("kitap", "Aboneliği");

        return beans.stream()
                .filter(bean -> bean.getPrice() >= 100)  // Filter out beans with price < 100
                .filter(bean -> excludedWords.stream().noneMatch(word -> bean.getName().toLowerCase().contains(word.toLowerCase())))  // Exclude unwanted words
                .sorted(Comparator.comparingInt(Bean::getPrice))  // Sort by price (ascending)
                .collect(Collectors.toList());
    }
}
