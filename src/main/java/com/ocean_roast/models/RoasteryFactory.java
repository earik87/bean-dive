package com.ocean_roast.models;


import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
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
                    "Ankara", ScrapeType.PARTIAL_CLASS_MATCH),
            new Roastery("Paper Roasting Coffee",
                    "https://paperroastingcoffee.com/kahve?o=3&page=1",
                    "Ankara", ScrapeType.JSON_LD_AND_HTML),
            new Roastery("Zumrut Karaca",
                    "https://www.zumrutkaraca.com/collections/frontpage",
                    "Ankara", ScrapeType.INLINE_JSON),
            new Roastery("Petra Coffee",
                    "https://www.petracoffee.com/collections/kahveler",
                    "Istanbul", ScrapeType.INLINE_JSON),
            new Roastery("Null Coffee",
                    "https://null.coffee/collections/kahveler",
                    "Istanbul", ScrapeType.INLINE_JSON),
            new Roastery("CoffeeProject",
                    "https://www.coffeeproject.com.tr/urun-kategori/kahveler",
                    "Istanbul", ScrapeType.SELECTOR_BASED)
);

    public static List<Roastery> getRoasteries() {
        return ROASTERIES;
    }
}