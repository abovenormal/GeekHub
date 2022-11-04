FROM openjdk:11-jdk
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} Task-Server-0.0.1-SNAPSHOT.jar
EXPOSE 9013/tcp
ENTRYPOINT ["java","-jar","/Task-Server-0.0.1-SNAPSHOT.jar"]