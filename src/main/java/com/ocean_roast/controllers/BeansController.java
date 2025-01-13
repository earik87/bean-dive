package com.ocean_roast.controllers;

import com.ocean_roast.models.Bean;
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
        return new ModelAndView("beans");
    }
}