package com.ocean_roast.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bean {
    private String roasteryName;
    private String name;
    private int price;
    private String link;
}