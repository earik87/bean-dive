package com.ocean_roast.controllers;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.RoasteryFactory;
import com.ocean_roast.services.TinCoffeeBeanScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BeansController {
    private final TinCoffeeBeanScraper coffeeBeanScraper;

    @GetMapping("/fetch-beans")
    public List<Bean> getBeans() {
        log.info("getBeans() called");
        List<Bean> beanPriceList = coffeeBeanScraper.fetchBeanPrices(RoasteryFactory.getRoasteries());
        return beanPriceList;
    }

    @Controller
    public static class BeansPageController {
        @GetMapping("/beans")
        public ModelAndView beansPage() {
            return new ModelAndView("beans");
        }
    }
}