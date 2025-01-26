package com.ocean_roast.controllers;

import com.ocean_roast.services.ScrapedDataCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BeansController {
    private final ScrapedDataCache scrapedDataCache;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("beans");
        modelAndView.addObject("pageTitle", "☕ Uncover New Roasts. Compare Prices. Brew Boldly.");
        modelAndView.addObject("beans", scrapedDataCache.getData());
        return modelAndView;
    }
}