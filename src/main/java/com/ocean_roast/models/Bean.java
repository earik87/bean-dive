package com.ocean_roast.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@RequiredArgsConstructor
public class Bean {
    private String roasteryName;
    private String name;
    private int price;
    private String link;

    public Bean(String roasteryName, String name, int price, String link) {
        this.roasteryName = roasteryName;
        this.name = name;
        this.price = price;
        this.link = link;
    }

}