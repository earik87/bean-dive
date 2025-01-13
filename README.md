# ocean-roast
Curated list of coffee roasters and coffee bean prices in Turkiye.

## Tech Stack
Backend
- Java 21
- Spring Boot 3.4.1
- Docker
Frontend
- Thymeleaf

## Running

### Local Development (testing UI)
- set spring.profiles.active to local in application.properties.
- mvn clean install
- run the application via intellij idea.
- open http://localhost:8080.

### QA Development (testing UI and scraper)
- set spring.profiles.active to qa in application.properties.
- mvn clean install
- docker build -t bean-app .
- docker run -d --name bean-app -p 8080:8080 -v $(pwd)/data:/data -e SPRING_PROFILES_ACTIVE=qa bean-app
- open http://localhost:8080.