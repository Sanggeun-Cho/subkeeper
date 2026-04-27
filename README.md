# SubKeeper 📝✨

<p align="center">
  <b>🚀 학기별 과제를 쉽고 체계적으로 관리할 수 있는 스마트 과제 매니저</b><br/>
  <sub>템플릿 기반 기록, 달력 뷰, 완료/미완료 구분까지 한 번에!</sub>
</p>

---

## 🌟 프로젝트 소개
**SubKeeper**는 대학생과 학습자를 위해 설계된 **과제 관리 플랫폼**입니다.  
과제와 할 일을 단순히 나열하는 것이 아니라 **블록 형태**로 기록하고,  
**과목 태그, 학기별 관리, 캘린더 뷰**를 통해 한눈에 확인할 수 있습니다.

> 복잡한 학사 일정 속에서 완료/미완료 구분과 체계적인 분류를 통해 더 이상 마감일을 놓치지 않게 도와줍니다!

---

## ✨ 주요 기능

### 🔑 Authentication & Security
- Spring Security 및 JWT를 활용한 안전한 토큰 기반 인증
- 비밀번호 등 민감 정보는 AES256 알고리즘 적용 및 암호화 관리
- 구글 OAuth 연동 (Google API Client 적용)

### 📚 Core Features
- **Semester (학기 관리)**: 학기 추가/삭제, 학기별 과제 및 과목 분리 관리
- **Subject (과목 관리)**: 과목 추가/삭제 및 태깅 기능
- **Assignment (과제 관리)**:
    - 과제 추가, 수정, 삭제
    - 카테고리 분류: `ASSIGNMENT` (일반 과제), `LECTURE` (강의 수강), `TODO` (기타 할 일)
    - 완료/미완료 (`isComplete`) 토글 기능
- **RESTful API & Swagger**: 프론트엔드 연동 및 확장을 위한 표준화된 REST API 제공 및 Swagger UI 지원
- **Server-Side Rendering**: Thymeleaf / Mustache 기반의 웹 페이지 뷰 (Dashboard, Login 등) 제공

### 🚀 Upcoming Features (진행 예정)
- **S3 첨부파일 관리**: 각 과제별 Detail 페이지에서 AWS S3를 활용한 다중 파일 업로드/다운로드 지원
- **AI 비서 (AI Assistant)**: LLM(Function Calling)을 활용해 사용자의 남은 과제 일정을 분석하고 조언을 제공하며, 자연어 명령으로 과제를 추가/수정하는 스마트 챗봇 도입

---

## 🛠 기술 스택

<p align="center">
  <!-- Backend -->
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot%203-6DB33F?logo=springboot&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Language-Java%2017-007396?logo=java&logoColor=white&style=for-the-badge"/>
  <!-- Database / ORM -->
  <img src="https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/ORM-Spring%20Data%20JPA-6DB33F?logo=spring&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/ORM-MyBatis-000000?logo=기타&logoColor=white&style=for-the-badge"/>
  <!-- Security -->
  <img src="https://img.shields.io/badge/Security-Spring%20Security-6DB33F?logo=springsecurity&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Security-JWT-000000?logo=jsonwebtokens&logoColor=white&style=for-the-badge"/>
  <!-- Tools -->
  <img src="https://img.shields.io/badge/Build-Gradle-02303A?logo=gradle&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/API-Swagger-85EA2D?logo=swagger&logoColor=black&style=for-the-badge"/>
</p>

---

## 🚀 시작하기

### 1. 클론하기
```bash
git clone <repository-url>
cd SubKeeper
```

### 2. 데이터베이스 설정 (MySQL)
MySQL에 애플리케이션용 데이터베이스를 생성합니다.
```sql
CREATE DATABASE assignment_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 3. application.yml 설정
`src/main/resources/application.yml` (또는 `.properties`) 파일을 생성하거나 열람하여 데이터베이스 및 시크릿 키 정보를 설정합니다.
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/assignment_db?serverTimezone=Asia/Seoul
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect

# JWT Secret, Google API Key 등 추가 설정 필요
```

### 4. 백엔드 빌드 및 실행
```bash
# Mac / Linux
./gradlew build
./gradlew bootRun

# Windows
gradlew.bat build
gradlew.bat bootRun
```

### 5. 접속 및 확인
- **웹 페이지**: `http://localhost:8080/`
- **Swagger API 문서**: `http://localhost:8080/swagger-ui/index.html` (또는 `/swagger-ui.html`)
