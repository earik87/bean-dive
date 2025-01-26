package com.ocean_roast.services.scraperstrategies;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.utils.PriceParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonLDAndHTMLCoffeeBeanScraper implements CoffeeBeanScraperInterface{

    @SneakyThrows
    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(roastery.getWebsite()).get();

            // Scrape HTML-based product listings
            Elements productElements = doc.select("div.style_infoContainer__bc4eW");

            for (Element productElement : productElements) {
                Element linkElement = productElement.selectFirst("a[style*=display:block]");
                Element nameElement = productElement.selectFirst("a.style_name__9PnR_");
                Element priceElement = productElement.selectFirst("div.style_price__Q7_0R");

                if (nameElement != null && priceElement != null && linkElement != null) {
                    String name = nameElement.text().trim();
                    String priceStr = priceElement.text().replaceAll("[^0-9,.]", "").trim();
                    String productUrl = linkElement.attr("href"); // Extract URL containing weight info

                    if (!name.isEmpty() && !priceStr.isEmpty()) {
                        int price = PriceParser.parsePrice(priceStr);
                        int adjustedPrice = adjustPriceBasedOnWeight(productUrl, price);
                        beans.add(new Bean(roastery.getName(), name, adjustedPrice, roastery.getWebsite()));
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return beans;
    }

    // Helper method to adjust the price based on detected weight in URL
    private int adjustPriceBasedOnWeight(String url, int price) {
        if (url.toLowerCase().contains("gramaj=1000-gr")) {
            return price / 4;  // Convert from 1000g to 250g
        } else if (url.toLowerCase().contains("gramaj=500-gr")) {
            return price / 2;  // Convert from 500g to 250g
        }
        return price;  // Default price (assumed to be 250g)
    }

}