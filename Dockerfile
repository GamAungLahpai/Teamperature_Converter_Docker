FROM maven:3.9.6-eclipse-temurin-21 AS build
LABEL authors="gamaung"

WORKDIR /app

COPY pom.xml .

COPY . /app

RUN mvn package

CMD ["java", "-jar", "target/TempConverter.jar"]
