FROM gradle:8.4.0-jdk17 AS build

WORKDIR /api

COPY build.gradle .
COPY settings.gradle .
COPY src /api/src

RUN gradle clean build -x test

FROM eclipse-temurin:17-jdk-alpine

COPY --from=build /api/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app.jar"]