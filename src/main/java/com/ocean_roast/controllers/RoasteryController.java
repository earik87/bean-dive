package com.ocean_roast.controllers;

import com.ocean_roast.models.Roastery;
import com.ocean_roast.models.RoasteryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoasteryController {

    @GetMapping("/roasteries")
    public List<Roastery> getRoasteries() {
        return RoasteryFactory.getRoasteries();
    }
}