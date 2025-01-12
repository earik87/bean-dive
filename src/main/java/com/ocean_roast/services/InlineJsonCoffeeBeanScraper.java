package com.ocean_roast.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InlineJsonCoffeeBeanScraper implements CoffeeBeanScraperInterface{

    @Override
    public List<Bean> fetchBeanPrices(Roastery roastery) {
        List<Bean> beans = new ArrayList<>();
        try {
            // 1) Fetch the page HTML
            Document doc = Jsoup.connect(roastery.getWebsite()).get();

            // 2) Grab the script by its id
            Element script = doc.getElementById("web-pixels-manager-setup");
            if (script == null) {
                System.out.println("No <script> with id='web-pixels-manager-setup' found!");
                return beans; // empty
            }

            String scriptContent = script.html();
            String marker = "publish(\"collection_viewed\",";
            int startIndex = scriptContent.indexOf(marker);
            startIndex += marker.length();
            int braceStart = scriptContent.indexOf('{', startIndex);
            int braceCount = 0;
            int i = braceStart;
            for (; i < scriptContent.length(); i++) {
                char c = scriptContent.charAt(i);
                if (c == '{') braceCount++;
                if (c == '}') braceCount--;
                if (braceCount == 0) {
                    break;
                }
            }

            String jsonChunk = scriptContent.substring(braceStart, i + 1).trim();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonChunk);

            JsonNode collectionNode = root.get("collection");
            if (collectionNode == null) {
                System.out.println("No 'collection' node in JSON!");
                return beans;
            }

            JsonNode productVariants = collectionNode.get("productVariants");
            if (productVariants == null || !productVariants.isArray()) {
                System.out.println("No 'productVariants' array found!");
                return beans;
            }

            for (JsonNode variant : productVariants) {
                String name = "";
                JsonNode productNode = variant.get("product");
                if (productNode != null && productNode.has("title")) {
                    name = productNode.get("title").asText();
                }

                int price = 0;
                JsonNode priceNode = variant.get("price");
                if (priceNode != null && priceNode.has("amount")) {
                    price = priceNode.get("amount").asInt();
                }

                beans.add(new Bean(roastery.getName(), name, price, roastery.getWebsite()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beans;
    }
}