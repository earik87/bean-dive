package com.ocean_roast.models;

import lombok.*;

@AllArgsConstructor
@Data
public class Roastery {
    private String name;
    private String website;
    private String location;
    private ScrapeType scrapeType;
}