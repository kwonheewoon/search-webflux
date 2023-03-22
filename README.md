# 블로그 검색 서비스 : 권희운

## 사용 외부 라이브러리
lombok : Java의 라이브러리로 반복되는 메소드를 Annotation을 사용해서 자동으로 작성해주는 라이브러리 \
mapstruct : Java bean 유형 간의 매핑 구현을 단순화하는 코드 생성기 

## jar 파일 위치
/search-module.jar

## 서버 구동 방법
java -jar /search-module.jar --spring.profiles.active=dev

## 빌드 방법
WORK_DIR :  search-module

jar 파일 빌드 \
./gradlew build


## 빌드 후 서버 구동 방법
빌드된 jar 파일 실행 \
java -jar ./build/libs/search-module-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev


##코드레벨 평가항목 구현사항
```c
-Spring Boot 기능 활용
    1.application-common.yml api.blog.main 의 값에 따른 KakaoBlogSearchService, 
        NaverBlogSearchService 구현체 의존성 전략 주입
    2.application-coom.yml의 api 설정값을 @ConfigurationProperties으로 설정값을 읽어
        PropertiesConfig 객체로 관리
        카카오, 네이버 API의 파라미터 쿼리 KEY, VALUE를 추상화 하려는 의도

-테스트 케이스
    1.search-module/src/test/io/khw/search/blogsearch 패키지 하위
        BlogSearchRestController, BlogSearchServiceTest 단위 테스트 작성
    2.search-module/src/test/io/khw/search/popularsearchkeyword 패키지 하위 
        popularsearchkeywordService 단위 테스트 작성
    3.search-module/src/test/io/khw/search/webclientTest 패키지 하위
        WebTestClient를 사용한 API 반환값 검증 테스트 작성
        
-에러처리
    1.common-module/src/java/io/khw/common/exeception
        ControllerAdvice 예외 핸들링 소스 작성
        ApiException 예외처리 메소드
        400 BAD_REQUEST 예외처리 메소드(ex) @RequestParam의 값 미존재시 발생)
        404 NotFound 예외처리 메소드
        RuntimeException 예외처리 메소드 작성

-불필요한 코드의 존재 여부
    1.최대한 불필요한 코드의 작성을 삼가하였으며
    2.WebClientCallManager, PopularSearchKeywordService
        의 소스와 같이 가독성 향상 및 재사용성을 위하여 함수 단위로 소스 작성 노력

```


## API 명세서
### Swagger Ui : http://localhost:8080/swagger-ui/index.html


### 카카오, 네이버 블로그 검색 API
```c
 1. application-common.yml api.blog.main 의 값에 따른 KakaoBlogSearchService, 
    NaverBlogSearchService 구현체 의존성 전략 주입
 2. main API의 서버 오류등 호출이 불가 할 겨우 지정된 횟수만큼 재시도
 3. 재시도 실패시 대체 API 재호출
 4. 호출이 성공시 query=검색어 의 검색횟수 증가
```

GET : localhost:8080/search/blog?query=검색어&sort=recency&page=1&size=10
```c
response : {
    "documents": [
        {
            "title": "충격!ChatGPT가 밝히는 구글 SEO전략의 실체!",
            "thumbnail": "",
            "blogName": "알쓸잡",
            "contents": "도와주는 방법입니다. 이를 위해서는 다음과 같은 전략을 사용할 수 있습니다. 1.키워드 연구: 사용자가 검색할 때 입력하는 단어나 구를 찾고, 그에 대한 <b>검색어</b>를 선정하여 적극적으로 활용합니다. 2.내부 링크 구축: 웹사이트 내의 페이지들 간에 링크를 구성하여 검색 엔진이 쉽게 페이지를 찾을 수 있도록 돕습니다...",
            "url": "http://wjdals10.tistory.com/2",
            "datetime": "2023-03-22T19:02:23"
        }
    ],
    "totalCount": 1219700
}
```

### 인기검색어 상위 10개 조회 API
GET : localhost:8080/search/blog/top-keywords
```c
response : {
    "documents": [
        {
            "keyword": "인천",
            "searchVolume": 11
        },
        {
            "keyword": "경기도",
            "searchVolume": 7
        },
        {
            "keyword": "판교",
            "searchVolume": 5
        }
    ],
    "totalCount": 3
} 
```

