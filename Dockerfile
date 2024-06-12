# Use a base image with Java 21
FROM openjdk:21-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file to the Docker image
COPY target/passwordManager.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
