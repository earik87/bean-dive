package com.ocean_roast;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoasteryService {
    public List<Roastery> getRoasteries() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new ClassPathResource("roasteries.json").getFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Roastery.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
