FROM openjdk:17-ea-11-jdk-slim
WORKDIR /server
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} board-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "board-service.jar"]