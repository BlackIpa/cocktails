# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Set environment variables for database connection
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://P:5432/cocktails
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

# Copy the application jar file to the container
COPY target/cocktails-0.0.1-SNAPSHOT.jar cocktails-0.0.1-SNAPSHOT.jar

# Expose port 8081
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "cocktails-0.0.1-SNAPSHOT.jar"]