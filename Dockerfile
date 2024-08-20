
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
RUN pwd
COPY . /app/
RUN ls -a
RUN mvn clean package -Dmaven.test.skip=true
FROM eclipse-temurin:21.0.3_9-jdk
WORKDIR /app

COPY --from=build /app/target/Game-0.0.1-SNAPSHOT.jar ./Game.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Game.jar"]
