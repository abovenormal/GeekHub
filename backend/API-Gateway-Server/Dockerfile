
FROM openjdk:11-jdk
ARG JAR_FILE_PATH=./build/libs/ApiGateway.jar
COPY ${JAR_FILE_PATH} APIGateway.jar
ENTRYPOINT ["java", "-jar", "APIGateway.jar"]