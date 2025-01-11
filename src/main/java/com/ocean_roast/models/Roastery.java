package com.ocean_roast.models;

import lombok.AllArgsConstructor;
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

    public Roastery(String name, String website, String location) {
        this.name = name;
        this.website = website;
        this.location = location;
    }
}