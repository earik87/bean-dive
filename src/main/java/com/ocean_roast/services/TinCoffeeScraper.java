package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
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
public class TinCoffeeScraper {

    private static final String URL = "https://tincoffee.net/urun-kategori/kahveler/";

    public List<Bean> fetchBeanPrices() {
        List<Bean> beans = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URL).get();
            Elements products = document.select("figcaption.woocom-list-content");

            for (Element product : products) {
                String name = product.select("h4.entry-title a").text();
                String price = product.select("span.price span.woocommerce-Price-amount").first().text();
                beans.add(new Bean(name, price));
            }
            log.info("Successfully fetched bean prices.");
        } catch (IOException e) {
            log.error("Error fetching data: {}", e.getMessage());
        }
        return beans;
    }
}