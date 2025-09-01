FROM ubuntu:latest
LABEL authors="it"

# syntax=docker/dockerfile:1
FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]