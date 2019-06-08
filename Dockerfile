# phase of building application
FROM openjdk:8-alpine as BUILD

WORKDIR /workspace

COPY gradle ./gradle
COPY gradlew ./
RUN ["./gradlew"]

COPY settings.gradle build.gradle ./
COPY src ./src
RUN ["./gradlew", "build"]


# phase of building docker image
FROM openjdk:8-jre-alpine

WORKDIR /app
RUN adduser -D -g '' sample && chown -R sample /app
USER sample

COPY --from=BUILD /workspace/build/libs/ktor-sample.jar ./app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
