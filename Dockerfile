# 1. 자바 17 버전을 기반으로 텅 빈 실행 환경을 가져옵니다.
FROM openjdk:17-jdk-jammy

# 2. 깃허브 가상 서버에서 빌드되어 생성될 jar 파일의 위치를 변수로 설정합니다.
ARG JAR_FILE=build/libs/*SNAPSHOT.jar

# 3. 완성된 jar 파일을 도커 이미지 내부로 복사하고 이름을 app.jar로 바꿉니다.
COPY ${JAR_FILE} app.jar

# 4. 도커 컨테이너가 켜질 때 이 애플리케이션을 자동으로 실행하도록 명령을 내립니다.
ENTRYPOINT ["java", "-jar", "/app.jar"]