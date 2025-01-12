package com.ocean_roast.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PriceParser {
    public static int parsePrice(String priceString) throws ParseException {
        if (priceString == null || priceString.isEmpty()) {
            return 0;
        }
        String numericPart = priceString.replaceAll("[^\\d.,-]", "");
        Locale localeUS = Locale.US;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(localeUS);
        Number number = numberFormat.parse(numericPart);
        return number.intValue();
    }
}
