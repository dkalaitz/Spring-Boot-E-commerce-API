# Use a base image with Java 21
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/eshop-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
