FROM openjdk:11-jdk
ARG JAR_FILE_PATH=./build/libs/Task-Server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]