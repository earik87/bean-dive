# Build Stage - Maven with Amazon Corretto 21
FROM maven:3.9.4-amazoncorretto-21 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage - Amazon Corretto 21
FROM amazoncorretto:21-alpine

WORKDIR /app

# Copy the built JAR
COPY --from=builder /app/target/ocean-roast-0.0.1-SNAPSHOT.jar app.jar

# Create a directory for persistent data
RUN mkdir -p /app/data

EXPOSE 8080

ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "/app/app.jar"]

CMD ["--spring.profiles.active=prod"]