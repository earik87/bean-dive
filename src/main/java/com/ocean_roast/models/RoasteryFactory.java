package com.ocean_roast.models;


import java.util.Arrays;
import java.util.List;

public class RoasteryFactory {
    public static final List<Roastery> ROASTERIES = Arrays.asList(
            new Roastery("Spada Coffee", "https://spadacoffee.com/", "Izmir"),
            new Roastery("CoffeeNutz", "https://www.coffeenutz.net/", "Izmir"),
            new Roastery("Incommon Coffee Roasters By Homestead", "https://homestead.coffee/", "Antalya"),
            new Roastery("Nitka Coffee&Roastery", "https://nitkacoffee.com/", "Ankara"),
            new Roastery("Tin Coffee", "https://tincoffee.net/", "Istanbul")
    );

    public static List<Roastery> getRoasteries() {
        return ROASTERIES;
    }
}