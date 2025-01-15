package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.utils.PriceParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PartialClassMatchCoffeeBeanScraper implements CoffeeBeanScraperInterface {

    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(roastery.getWebsite()).get();

            // Matches classes starting with 'product-card_infoContainer'
            Elements products = doc.select("[class^=product-card_infoContainer]");

            for (Element product : products) {
                // Matches classes starting with 'product-card_name'
                String name = product.select("[class^=product-card_name]").text();
                // Matches classes starting with 'product-card_price'
                String price = product.select("[class^=product-card_price]").text();
                // Create our Bean object
                if (!name.isEmpty() && !price.isEmpty()) {
                    // Create our Bean object
                    Bean bean = new Bean(roastery.getName(), name, PriceParser.parsePrice(price), roastery.getWebsite());
                    beans.add(bean);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return beans;
    }
}