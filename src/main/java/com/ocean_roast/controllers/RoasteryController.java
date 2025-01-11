package com.ocean_roast.controllers;

import com.ocean_roast.models.Roastery;
import com.ocean_roast.models.RoasteryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoasteryController {

    @GetMapping("/roasteries")

    public List<Roastery> getRoasteries() {
        log.info("getRoasteries() called");
        return RoasteryFactory.getRoasteries();
    }
}