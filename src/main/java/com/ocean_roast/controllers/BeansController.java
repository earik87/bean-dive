package com.ocean_roast.controllers;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.Roastery;
import com.ocean_roast.services.RoasteryFactory;
import com.ocean_roast.services.ScrapedDataCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BeansController {
    private final ScrapedDataCache scrapedDataCache;

    @GetMapping("/beans")
    public List<Bean> getBeans() {
        return scrapedDataCache.getCachedBeans();
    }

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("beans");
        modelAndView.addObject("pageTitle", "☕ Uncover New Roasts. Compare Prices. Brew Boldly.");
        return modelAndView;
    }

    @GetMapping("/getRoasteries")
    public List<Roastery> getRoasteries() {
        return RoasteryFactory.getRoasteries();
    }

    @GetMapping("/roastery")
    public ModelAndView roasteryPage() {
        ModelAndView modelAndView = new ModelAndView("roasteries");
        modelAndView.addObject("pageTitle", "☕ Discover Roasteries.");
        return modelAndView;
    }
}