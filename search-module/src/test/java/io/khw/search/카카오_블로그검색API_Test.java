package io.khw.search;import io.khw.SearchModuleApplication;import io.netty.channel.ChannelOption;import io.netty.handler.timeout.ReadTimeoutHandler;import io.netty.handler.timeout.WriteTimeoutHandler;import org.junit.jupiter.api.BeforeEach;import org.junit.jupiter.api.Test;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.http.HttpHeaders;import org.springframework.http.MediaType;import org.springframework.http.client.reactive.ReactorClientHttpConnector;import org.springframework.test.context.ActiveProfiles;import org.springframework.test.web.reactive.server.WebTestClient;import org.springframework.web.reactive.function.client.ExchangeStrategies;import org.springframework.web.reactive.function.client.WebClient;import reactor.netty.http.client.HttpClient;import java.time.Duration;@SpringBootTest(classes = SearchModuleApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)@ActiveProfiles("local")public class 카카오_블로그검색API_Test {    private WebTestClient webTestClient;    private WebClient webClient;    @BeforeEach    void setUp() {        webTestClient = WebTestClient.bindToServer().baseUrl("https://dapi.kakao.com").build();    }    @Test    public void 카카오_블로그_검색API_테스트() {        long startTime = System.currentTimeMillis();        // HTTP GET 요청 보내기        webTestClient.get()                .uri(uriBuilder -> uriBuilder                        .path("/v2/search/web")                        .queryParam("query", "sdfsdf")                        .queryParam("sort", "recency")                        .queryParam("page", "1")                        .queryParam("size", "10")                        .build())                .header(HttpHeaders.AUTHORIZATION, "KakaoAK 298b3a98921581d3ae42613a4003d641")                .accept(MediaType.APPLICATION_JSON)                .exchange()                .expectStatus().isOk()                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)                .expectBody()                .jsonPath("$.meta.total_count").isNumber()                .jsonPath("$.meta.pageable_count").isNumber()                .jsonPath("$.meta.is_end").isBoolean()                .jsonPath("$.documents[0].title").isNotEmpty()                .jsonPath("$.documents[0].contents").isNotEmpty()                .jsonPath("$.documents[0].url").isNotEmpty()                .jsonPath("$.documents[0].datetime").isNotEmpty();        long elapsedTime = System.currentTimeMillis() - startTime;        System.out.println("Elapsed time: " + elapsedTime + "ms");    }    @Test    public void 카카오_블로그_검색API_에러메시지_반환_테스트() {        long startTime = System.currentTimeMillis();        // HTTP GET 요청 보내기        webTestClient.get()                .uri(uriBuilder -> uriBuilder                        .path("/v2/search/web")                        .queryParam("query", "")                        .queryParam("sort", "recency")                        .queryParam("page", "1")                        .queryParam("size", "10")                        .build())                .header(HttpHeaders.AUTHORIZATION, "KakaoAK 298b3a98921581d3ae42613a4003d641")                .accept(MediaType.APPLICATION_JSON)                .exchange()                .expectStatus().isBadRequest()                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)                .expectBody()                .jsonPath("$.errorType").isEqualTo("MissingParameter")                .jsonPath("$.message").isEqualTo("query parameter required");        long elapsedTime = System.currentTimeMillis() - startTime;        System.out.println("Elapsed time: " + elapsedTime + "ms");    }}