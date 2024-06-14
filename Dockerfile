FROM openjdk:17-jdk-slim-buster

COPY /target/*.jar car-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "car-service.jar"]