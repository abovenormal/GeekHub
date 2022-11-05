// 생성할 도커이미지의 베이스가 될 자바 jdk 버전
FROM openjdk:11-jdk
// 빌드 시 jar파일이 2개 생성되어서 파일명을 직접 지정해주었다
// jar파일이 1개만 생성된다면 *로해도 무방
ARG JAR_FILE_PATH=./build/libs/APIGateway.jar
// 해당 위치의 지정된 파일을 app.jar이란 이름으로 복사하겠다
COPY ${JAR_FILE_PATH} APIGateway.jar
// 실행시킬 파일의 유형과 명칭을 지정하는 명령어
ENTRYPOINT ["java", "-jar", "APIGateway.jar"]