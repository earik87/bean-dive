package com.ocean_roast.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.utils.PriceParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonLDCoffeeBeanScraper implements CoffeeBeanScraperInterface{
    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            // 1) Fetch the page
            Document doc = Jsoup.connect(roastery.getWebsite()).get();

            // 2) There may be multiple <script type="application/ld+json"> blocks.
            //    We'll select them all and see which one has "ItemList".
            //    We'll parse each one until we find "itemListElement" with products.

            for (Element script : doc.select("script[type=application/ld+json]")) {
                String jsonString = script.html(); // the raw JSON in the <script>

                // 3) Parse the JSON with Jackson
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonString);

                // 4) Check if this block has "itemListElement" (i.e., is it the ItemList?)
                JsonNode itemList = rootNode.get("itemListElement");
                if (itemList != null && itemList.isArray()) {
                    // We found the ItemList data
                    for (JsonNode listItem : itemList) {
                        // Each entry like: { "@type":"ListItem", "position":..., "item": { ... } }
                        JsonNode productNode = listItem.get("item");
                        if (productNode != null) {
                            // "name" is in productNode.get("name")
                            JsonNode nameNode = productNode.get("name");
                            String name = nameNode != null ? nameNode.asText() : "";

                            // "offers" -> "price"
                            JsonNode offersNode = productNode.get("offers");
                            String price = "";
                            if (offersNode != null && offersNode.get("price") != null) {
                                price = offersNode.get("price").asText(); // e.g. "525.00"
                            }
                            if (name != null && !name.isEmpty() && price != null && !price.isEmpty()) {
                                // Create our Bean object
                                Bean bean = new Bean(roastery.getName(), name, PriceParser.parsePrice(price), roastery.getWebsite());
                                beans.add(bean);
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return beans;
    }
}
