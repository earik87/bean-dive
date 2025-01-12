package com.ocean_roast.services;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;

import java.util.List;

public interface CoffeeBeanScraperInterface {
    List<Bean> fetchBeanPrices(List<Roastery> roasteries);
}