# 장소 검색 서비스 서버

## 서버 스펙
- Kotlin
- Jpa
- Gradle
- H2 DB
- Kotest
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
- 설명
```
원하는 키워드(축구)로 장소를 검색하면 카카오 API, 네이버 API를 이용하여 장소를 검색
만약 카카오, 네이버 API의 검색 결과가 카카오 5개, 네이버 5개 총 10개가 되지 않는다면
부족한 API는 한번 더 조회해도 같은 결과가 나올것이기 때문에 다른 API를 조회하여 10개의 결과를 채운다.

여러 명이 동시에 '축구'라는 키워드로 장소를 검색 했을 경우 검색 이력이 생성이 되어야하는데
검색 키워드가 유니크 하기 때문에 에러가 발생합니다. 
그 상황을 대비하기 위해 재시도하여 에러가 발생한 요청은 검색 이력의 검색 수를 업데이트합니다. 
```
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
- 설명
```
가장 많이 검색한 키워드 중 10개를 선별하여 응답 값으로 내려줍니다. 
```
- Path : http://localhost:9898/api/search/histories
- 테스트 CURL
```
  curl -X GET \
  -H "Content-Type: application/json" \
  -H "Authorization: kb-it-compliance-test" \
  http://localhost:9898/api/search/histories
```
