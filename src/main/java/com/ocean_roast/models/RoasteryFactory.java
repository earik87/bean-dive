package com.ocean_roast.models;


import java.util.Arrays;
import java.util.List;

public class RoasteryFactory {
    public static final List<Roastery> ROASTERIES = Arrays.asList(
            new Roastery("Tin Coffee",
                    "https://tincoffee.net/urun-kategori/kahveler/",
                    "Istanbul", ScrapeType.HTML),
            new Roastery("Spada Coffee",
                    "https://spadacoffee.com/sezonsal-kahveler",
                    "Izmir", ScrapeType.JSON_LD),
            new Roastery("CoffeeNutz",
                    "https://www.coffeenutz.net/collections/kahve",
                    "Izmir", ScrapeType.INLINE_JSON),
            new Roastery("Incommon Coffee Roasters By Homestead",
                    "https://homestead.coffee/collections/specialty",
                    "Antalya", ScrapeType.PRODUCT_CARD),
            new Roastery("Nitka Coffee&Roastery",
                    "https://nitkacoffee.com/pages/cekirdek-kahveler",
                    "Ankara", ScrapeType.PARTIAL_CLASS_MATCH)
    );

    public static List<Roastery> getRoasteries() {
        return ROASTERIES;
    }
}