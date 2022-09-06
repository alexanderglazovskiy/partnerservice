# syntax=docker/dockerfile:1
FROM gradle:7.5.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM bellsoft/liberica-openjdk-alpine:11
COPY --from=build /home/gradle/src/build/libs/limit-0.0.1-SNAPSHOT.jar limit-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/limit-0.0.1-SNAPSHOT.jar"]