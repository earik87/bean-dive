package com.ocean_roast.models;


import java.util.Arrays;
import java.util.List;

public class RoasteryFactory {
    public static final List<Roastery> ROASTERIES = Arrays.asList(
            new Roastery("Tin Coffee", "https://tincoffee.net/urun-kategori/kahveler/", "Istanbul")
//            new Roastery("Spada Coffee", "https://spadacoffee.com/", "Izmir"),
//            new Roastery("CoffeeNutz", "https://www.coffeenutz.net/collections/kahve", "Izmir")
//            new Roastery("Incommon Coffee Roasters By Homestead", "https://homestead.coffee/", "Antalya"),
//            new Roastery("Nitka Coffee&Roastery", "https://nitkacoffee.com/", "Ankara")
    );

    public static List<Roastery> getRoasteries() {
        return ROASTERIES;
    }
}