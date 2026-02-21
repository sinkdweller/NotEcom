# Java Runtime Base Image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the compiled JAR file from  target folder to the container
COPY target/*.jar app.jar

# Run
ENTRYPOINT ["java", "-jar", "app.jar"]