FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar cocktails-app.jar

EXPOSE 8081

CMD ["java", "-jar", "cocktails-app.jar"]