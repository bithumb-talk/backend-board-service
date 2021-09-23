FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]