FROM openjdk:11-jdk
ARG JAR_FILE=Task-Server/build/libs/*.jar
COPY ${JAR_FILE} *.jar
EXPOSE 9013/tcp
ENTRYPOINT ["java","-jar","app.jar"]