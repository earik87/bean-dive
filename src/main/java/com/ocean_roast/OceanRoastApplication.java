package com.ocean_roast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OceanRoastApplication {

	public static void main(String[] args) {
		SpringApplication.run(OceanRoastApplication.class, args);
	}

}
