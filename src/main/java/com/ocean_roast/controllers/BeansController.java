package com.ocean_roast.controllers;

import com.ocean_roast.models.Bean;
import com.ocean_roast.models.RoasteryFactory;
import com.ocean_roast.services.ScrapedDataCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BeansController {

    private final ScrapedDataCache scrapedDataCache;
    private final RoasteryFactory roasteryFactory;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("beans");
        List<Bean> beans = scrapedDataCache.getData();

        // Calculate analytics data
        int totalRoasteries = roasteryFactory.getRoasteries().size();
        int successfulRoasteries = (int) beans.stream()
                .map(Bean::getRoasteryName)
                .distinct()
                .count();
        int beanCount = beans.size();

        modelAndView.addObject("pageTitle", "☕ Uncover New Roasts. Compare Prices. Brew Boldly.");
        modelAndView.addObject("beans", beans);
        modelAndView.addObject("successfulRoasteries", successfulRoasteries);
        modelAndView.addObject("totalRoasteries", totalRoasteries);
        modelAndView.addObject("beanCount", beanCount);

        return modelAndView;
    }
}