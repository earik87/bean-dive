package com.ocean_roast.controllers;

import com.ocean_roast.models.Roastery;
import com.ocean_roast.services.RoasteryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class RoasteryController {

    @GetMapping("/roasteries")
    public String roasteries(Model model) {
        List<Roastery> roasteries = RoasteryFactory.getRoasteries();
        model.addAttribute("roasteries", roasteries);
        model.addAttribute("pageTitle", "☕ Discover Roasteries");
        return "roasteries";
    }
}