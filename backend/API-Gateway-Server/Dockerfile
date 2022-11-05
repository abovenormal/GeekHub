
FROM openjdk:11-jdk
ARG JAR_FILE_PATH=./build/libs/APIGateway.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]