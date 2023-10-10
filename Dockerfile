FROM maven:3.8.5-openjdk-17 as build

WORKDIR /usr/src/app
COPY .. /usr/src/app
RUN mvn package -DskipTests

FROM eclipse-temurin:17-alpine
ARG JAR_FILE=ekwateur-invoice.jar
WORKDIR /opt/app
COPY --from=build /usr/src/app/target/${JAR_FILE} /opt/app/application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]