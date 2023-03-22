package io.khw.search.blogsearch.service.impl;import io.khw.common.config.PropertiesConfig;import io.khw.domain.blogsearch.dto.AnotherApiResponseDto;import io.khw.domain.blogsearch.dto.ApiResponseDto;import io.khw.domain.blogsearch.dto.CommonApiResponseDto;import io.khw.search.blogsearch.service.BlogSearchService;import io.khw.domain.blogsearch.vo.SearchVo;import io.khw.search.common.WebClientCallManager;import io.khw.search.popularsearchkeyword.service.PopularSearchKeywordService;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;import org.springframework.web.reactive.function.client.WebClient;import reactor.core.publisher.Mono;@Service@RequiredArgsConstructorpublic class NaverBlogSearchService implements BlogSearchService {    private final WebClientCallManager webClientCallManager;    private final WebClient.Builder webClientBuilder;    private final PropertiesConfig propertiesConfig;    private final PopularSearchKeywordService popularSearchKeywordService;    public Mono<SearchVo> searchTest(SearchVo searchVo){        popularSearchKeywordService.incrementSearchCount(searchVo.query());        return Mono.just(searchVo);    }    /**     * 네이버 블로그 검색 API     *     * 1.첫 번째 API(네이버)에 요청을 보낸다. 응답을 AnotherApiResponseDto 유형으로 변환     * 2 retryWhen()을 사용하여, 지정된 재시도 정책에 따라 네이버 API 요청을 재시도 최대 2번의 재시도를 2초 간격으로 수행     * 3.onErrorResume()을 사용하여, 네이버 API 요청에서 오류가 발생한 경우 처리를 정의 오류가 발생하면, 카카오 API에 대한 요청을 전송하고,     *   응답을 ApiResponseDto 유형으로 변환한 다음, ApiResponseDto::toCommon 함수를 사용하여 공통 응답 유형으로 변환     * 4.doOnSuccess()를 사용하여, 검색 결과가 성공적으로 반환되면, popularSearchKeywordService.incrementSearchCount() 메소드를 호출하여 검색 키워드의 검색 횟수를 증가     *     * @param searchVo 검색 객체     * @return Mono<CommonApiResponseDto> API 정보 객체 반환     */    @Override    public Mono<CommonApiResponseDto> search(SearchVo searchVo) {        return webClientCallManager.getCallExchangeApi(webClientBuilder.build(), buildApiUri(propertiesConfig.getNaver(), searchVo), propertiesConfig.getNaver().getHeaders(), AnotherApiResponseDto.class, AnotherApiResponseDto::toCommon)                .retryWhen(webClientCallManager.handleRetry(2,2))                .onErrorResume(e -> webClientCallManager.handleErrorResume(e, webClientBuilder.build(), buildApiUri(propertiesConfig.getKakao(), searchVo), propertiesConfig.getKakao().getHeaders(), ApiResponseDto.class, ApiResponseDto::toCommon))                .doOnSuccess(response -> popularSearchKeywordService.incrementSearchCount(searchVo.query()));    }}