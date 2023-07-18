# 장소 검색 서비스 서버

## 서버 스펙
- Kotlin
- Jpa
- Gradle
- H2 DB
- Swagger : http://localhost:9898/swagger-ui/index.html
- Port : 9898

## 사용 라이브러리
- spring-boot-starter-webflux : WebClient를 이용하기 위함
- spring-aspects : Retry를 이용하기 위함
- spring-retry : 무결설 제약 조건 위배, Dead Lock 상황 시 재시도를 위함
- jackson-datatype-jsr310 : Json Date 관련 직렬화/역직렬화를 위함
- h2 : 검색 이력을 저장하기 위해 메모리 DB 사용
- koin-test : kotlin에 어울리며 given, when, then을 표현하기 용이해 사용
- mockk : Test시 모의 객체를 사용하기 위함

## 테스트 방법

### 키워드 장소 검색
- Path : http://localhost:9898/api/local/search
- 일반 테스트 CURL
  ```
  curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: kb-it-compliance-test" \
  -d '{"query": "축구"}' \
  http://localhost:9898/api/local/search
  ```
- 동시성 테스트 CURL
  ```
  curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: kb-it-compliance-test" \
  -d '{"query": "축구"}' \
  http://localhost:9898/api/local/search &
  curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: kb-it-compliance-test" \
  -d '{"query": "축구"}' \
  http://localhost:9898/api/local/search &
  wait
  ``` 

### 키워드 검색 이력
- Path : http://localhost:9898/api/search/histories
- 테스트 CURL
  ```
    curl -X GET \
    -H "Content-Type: application/json" \
    -H "Authorization: kb-it-compliance-test" \
    http://localhost:9898/api/search/histories
  ```
