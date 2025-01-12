package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TinCoffeeBeanScraper implements CoffeeBeanScraperInterface {

    @Override
    public List<Bean> fetchBeanPrices(List<Roastery> roasteries) {
        List<Bean> beans = new ArrayList<>();
        for (Roastery roastery : roasteries) {
            try {
                Document document = Jsoup.connect(roastery.getWebsite()).get();
                Elements products = document.select("figcaption.woocom-list-content");

                for (Element product : products) {
                    String name = product.select("h4.entry-title a").text();
                    String price = product.select("span.price span.woocommerce-Price-amount").first().text();
                    beans.add(new Bean(roastery.getName(), name, price, roastery.getWebsite()));
                }
                log.info("Successfully fetched bean prices.");
            } catch (IOException e) {
                log.error("Error fetching data: {}", e.getMessage());
            }
        }
        return beans;
    }
}