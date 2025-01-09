package com.ocean_roast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Roastery {
    // Getters and setters
    @JsonProperty("name")
    private String name;

    @JsonProperty("website")
    private String website;

    @JsonProperty("location")
    private String location;
}