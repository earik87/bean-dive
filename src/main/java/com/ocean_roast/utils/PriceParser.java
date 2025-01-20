package com.ocean_roast.utils;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PriceParser {
    public static int parsePrice(String priceString) throws ParseException {
        if (priceString == null || priceString.trim().isEmpty()) {
            return 0;
        }

        // Remove currency symbols and keep only numeric values, commas, and dots
        String cleanedPrice = priceString.replaceAll("[^0-9.,]", "").trim();

        // Ensure we extract only the first valid price if duplicates exist
        Pattern pattern = Pattern.compile("\\d+[,.]?\\d*");
        Matcher matcher = pattern.matcher(cleanedPrice);
        if (matcher.find()) {
            cleanedPrice = matcher.group(); // Extract first number occurrence
        }

        // Handle different formats
        if (cleanedPrice.contains(",") && cleanedPrice.contains(".")) {
            // Case like 1.250,50 -> Assume dot is thousands separator, replace dot and comma properly
            cleanedPrice = cleanedPrice.replace(".", "").replace(",", ".");
        } else if (cleanedPrice.contains(",")) {
            // Case like 1250,50 -> Convert comma to decimal point
            cleanedPrice = cleanedPrice.replace(",", ".");
        } else if (cleanedPrice.contains(".")) {
            // Case like 1250.50 -> Likely a correct decimal format
        }

        // Convert to double, round, and return integer value
        try {
            double price = Double.parseDouble(cleanedPrice);
            return (int) Math.round(price);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid price format: " + priceString, 0);
        }
    }
}