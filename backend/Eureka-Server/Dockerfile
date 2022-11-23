FROM openjdk:11-jdk
ARG JAR_FILE_PATH=./build/libs/Eureka.jar
COPY ${JAR_FILE_PATH} Eureka.jar
ENTRYPOINT ["java", "-jar", "Eureka.jar"]