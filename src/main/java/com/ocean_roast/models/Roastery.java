package com.ocean_roast.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class Roastery {
    private String name;
    private String website;
    private String location;
    private ScrapeType scrapeType;

    public Roastery(String name, String website, String location, ScrapeType scrapeType) {
        this.name = name;
        this.website = website;
        this.location = location;
        this.scrapeType = scrapeType;
    }
}