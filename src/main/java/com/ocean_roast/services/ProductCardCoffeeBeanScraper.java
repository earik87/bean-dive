package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.utils.PriceParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProductCardCoffeeBeanScraper implements CoffeeBeanScraperInterface {

    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            // Connect to the website with a user-agent to avoid 403 errors
            Document doc = Jsoup.connect(roastery.getWebsite())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.5993.70 Safari/537.36")
                    .timeout(10000)
                    .referrer("https://www.google.com/")
                    .get();

            // Select all product containers
            Elements products = doc.select(".product-card__info");

            for (Element product : products) {
                // Extract product name and link
                String name = product.select(".product-card__name").text();
//                String link = product.select(".product-card__name").attr("href");

                // Extract product price
                String price = product.select(".product-card__price").text();
                if (!name.isEmpty() && !price.isEmpty()) {
                    // Create our Bean object
                    Bean bean = new Bean(roastery.getName(), name, PriceParser.parsePrice(price), roastery.getWebsite());
                    beans.add(bean);
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return beans;
    }
}