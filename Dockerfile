FROM eclipse-temurin:24-jdk AS build

COPY . .

RUN apt-get update && apt-get install maven -y
RUN mvn clean install

FROM eclipse-temurin:24-jdk

EXPOSE 8080

COPY --from=build /target/taskium-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]