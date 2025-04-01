FROM eclipse-temurin:21.0.6_7-jdk-alpine-3.21 AS build

WORKDIR /app

COPY pom.xml ./
COPY mvnw ./
COPY .mvn ./.mvn
COPY src ./src/

RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
