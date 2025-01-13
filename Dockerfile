# Use an official Amazon Corretto 21 image
FROM amazoncorretto:21-alpine

# Create app directory in container
WORKDIR /app

# Copy the JAR (assuming you've already built with 'mvn clean package')
COPY target/ocean-roast-0.0.1-SNAPSHOT.jar app.jar

# Create a directory for storing scraped data
RUN mkdir /data

# Optional: Set permissions for /data (if needed)
RUN chmod -R 755 /data

# Declare /data as a volume to persist data
VOLUME /data

# Expose the port your Spring Boot app listens on (default 8080)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
