FROM maven:3.9.4-eclipse-temurin-17 as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package dependency:copy-dependencies -DincludeScope=runtime

FROM bellsoft/liberica-openjdk-debian:17

RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot

WORKDIR /app

RUN mkdir -p /app/logs

COPY --from=build target/dependency ./lib
COPY --from=build target/audio_file_manager.jar ./application.jar

ENTRYPOINT ["java","-jar","application.jar"]