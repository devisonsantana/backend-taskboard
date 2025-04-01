FROM ubuntu:latest AS build

RUN apt-get update && apt-get install openjdk-21-jdk -y

COPY . .

RUN apt-get install maven -y

RUN mvn clean install -DskipTest

FROM eclipse-temurin:21-alpine

EXPOSE 8080

COPY --from=build /target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
