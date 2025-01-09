package com.ocean_roast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
public class RoasteryController {
    @Autowired
    RoasteryService roasteryService;

    @GetMapping("/roasteries")
    public List<Roastery> getRoasteries() throws IOException {
        return roasteryService.getRoasteries();
    }
}