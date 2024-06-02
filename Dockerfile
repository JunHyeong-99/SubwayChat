# gradle:7.6-jdk17 이미지를 기반으로 함
FROM gradle:7.6-jdk17 AS builder

# 작업 디렉토리 설정
WORKDIR /project

# Gradle 캐시를 활용하기 위해 빌드 스크립트를 먼저 복사
COPY build.gradle settings.gradle /project/

# 의존성 다운로드
RUN gradle build --no-daemon --parallel --info || return 0

# 소스 코드 복사
COPY . .

# gradle 빌드
RUN gradle clean build -x test

# 런타임 이미지를 생성
FROM openjdk:17-jdk-slim AS runtime

# 빌드된 JAR 파일을 복사
COPY --from=builder /project/build/libs/SubwayChat-0.0.1-SNAPSHOT.jar /app/SubwayChat-0.0.1-SNAPSHOT.jar

# 빌드 결과 JAR 파일을 실행
CMD ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
