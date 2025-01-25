package com.ocean_roast.services.scrapedDataService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ocean_roast.models.Bean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BeanPriceDataService {

    private static final String DATA_FILE_PATH = "/app/data/scraped_data.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Bean> loadData() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            log.info("No existing data file found. Starting with empty data.");
            return new ArrayList<>();
        }

        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(file, typeFactory.constructCollectionType(List.class, Bean.class));
        } catch (IOException e) {
            log.error("Error reading data file: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveData(List<Bean> beans) {
        try {
            objectMapper.writeValue(new File(DATA_FILE_PATH), beans);
            log.info("Data successfully saved to file.");
        } catch (IOException e) {
            log.error("Error writing data to file: {}", e.getMessage());
        }
    }
}