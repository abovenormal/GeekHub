FROM openjdk:11-jdk
ARG JAR_FILE=./build/libs/
COPY ${JAR_FILE} Task-Server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]