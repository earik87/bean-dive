package com.ocean_roast;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.util.List;

@RestController
public class RoasteryController {

    @GetMapping("/roasteries")
    private List<Roastery> getRoasteries() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new ClassPathResource("roasteries.json").getFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Roastery.class));
    }
}