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
    private String name;
    private String price;

    public Bean(String name, String price) {
        this.name = name;
        this.price = price;
    }

}