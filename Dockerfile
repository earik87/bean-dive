# Use an official Amazon Corretto 21 image
FROM amazoncorretto:21-alpine

# Create app directory in container
WORKDIR /app

# Copy the JAR (assuming you've already built with 'mvn clean package')
COPY target/ocean-roast-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app listens on (default 8080)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "/app/app.jar"]
