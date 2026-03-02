FROM openjdk:21-slim

WORKDIR /app


COPY target/TempConverter.jar /app/TempConverter.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/TempConverter.jar"]
