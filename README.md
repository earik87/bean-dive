# bean-dive
Curated list of coffee roasters and coffee bean prices in Turkiye. It is live in here -> https://www.beandive.com/

## Tech Stack
Backend
- Java 21
- Spring Boot 3.4.1
- Docker

Frontend
- Thymeleaf

Cloud provider
- Render

## Running

### Local Development 
#### Testing UI
- set spring.profiles.active to test in application.properties.
- mvn clean install
- run the application via intellij idea.
- open http://localhost:8080

#### Testing UI and Scraper
- set spring.profiles.active to prod in application.properties.
- maybe select only few roasteries from roasterFactory file.
- mvn clean install
- docker build -t bean-app .
- docker run -d --name bean-app -p 8080:8080 bean-app
- open http://localhost:8080
