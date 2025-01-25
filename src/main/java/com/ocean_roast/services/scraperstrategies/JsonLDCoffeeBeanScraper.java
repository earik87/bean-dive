package com.ocean_roast.services.scraperstrategies;

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
            Document doc = Jsoup.connect(roastery.getWebsite()).get();
            for (Element script : doc.select("script[type=application/ld+json]")) {
                String jsonString = script.html(); // the raw JSON in the <script>
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonString);
                JsonNode itemList = rootNode.get("itemListElement");
                if (itemList != null && itemList.isArray()) {
                    for (JsonNode listItem : itemList) {
                        JsonNode productNode = listItem.get("item");
                        if (productNode != null) {
                            JsonNode nameNode = productNode.get("name");
                            String name = nameNode != null ? nameNode.asText() : "";
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
