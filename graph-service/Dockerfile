FROM eclipse-temurin:17-jdk-alpine as build

ARG gavVersion=0.0.1-SNAPSHOT

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests
RUN jar -xvf target/graph-service-${gavVersion}.jar

FROM eclipse-temurin:17-jdk-alpine as runtime

COPY --from=build /app /app/

CMD java $JAVA_OPTS -cp /app/ org.springframework.boot.loader.JarLauncher