package com.ocean_roast.services.scraperstrategies;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.utils.PriceParser;
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
@Slf4j
public class SelectorBasedCoffeeBeanScraper implements CoffeeBeanScraperInterface {
    @SneakyThrows
    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(roastery.getWebsite()).get();

            // Select all product containers
            Elements products = doc.select("div.productName.detailUrl");

            for (Element product : products) {
                // Extract coffee name
                Element nameElement = product.selectFirst("a");
                String name = nameElement != null ? nameElement.text().trim() : "Unknown";

                // Extract corresponding price
                Element priceElement = product.parent().selectFirst("div.productPrice .discountPriceSpan");
                String price = priceElement != null ? priceElement.text().replaceAll("[^0-9,.]", "").trim() : "0.00";
                int priceInt = PriceParser.parsePrice(price);
                beans.add(new Bean(roastery.getName(), name, priceInt, roastery.getWebsite()));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return beans;
    }
}
