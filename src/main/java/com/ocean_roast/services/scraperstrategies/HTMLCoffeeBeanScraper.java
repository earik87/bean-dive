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

@Slf4j
@Component
@RequiredArgsConstructor
public class HTMLCoffeeBeanScraper implements CoffeeBeanScraperInterface {

    @SneakyThrows
    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            Document document = Jsoup.connect(roastery.getWebsite()).get();
            Elements products = document.select("figcaption.woocom-list-content");

            for (Element product : products) {
                String name = product.select("h4.entry-title a").text();
                int price = PriceParser.parsePrice(product.select("span.price span.woocommerce-Price-amount").first().text());

                beans.add(new Bean(roastery.getName(), name, price, roastery.getWebsite()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return beans;
    }
}