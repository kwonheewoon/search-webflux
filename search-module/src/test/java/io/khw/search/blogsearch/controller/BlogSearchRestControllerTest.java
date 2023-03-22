package io.khw.search.blogsearch.controller;


import io.khw.domain.blogsearch.dto.CommonApiResponseDto;
import io.khw.domain.blogsearch.dto.CommonDocumentDto;
import io.khw.domain.blogsearch.vo.SearchVo;
import io.khw.domain.popularsearchkeyword.dto.PopularSearchKeywordApiDto;
import io.khw.search.blogsearch.service.BlogSearchService;
import io.khw.search.popularsearchkeyword.service.PopularSearchKeywordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlogSearchRestController.class)
@MockBean(JpaMetamodelMappingContext.class)

public class BlogSearchRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogSearchService blogSearchService;

    @MockBean
    private PopularSearchKeywordService popularSearchKeywordService;

    @Test
    public void searchBlogTopKeywordsTest() throws Exception {
        List<PopularSearchKeywordApiDto> searchKeywordApiDtos = Arrays.asList(
                new PopularSearchKeywordApiDto("인천", 3)
        );

        when(popularSearchKeywordService.getTopKeyWords()).thenReturn(searchKeywordApiDtos);

        mockMvc.perform(get("/blog/top-keywords")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documents[0].keyword").value("인천"))
                .andExpect(jsonPath("$.documents[0].searchVolume").value(3))
                .andExpect(jsonPath("$.totalCount").value(1));
    }

    @Test
    public void searchBlogTest() throws Exception {
        CommonApiResponseDto response = new CommonApiResponseDto(
                Arrays.asList(new CommonDocumentDto("충격!ChatGPT가 밝히는 구글 SEO전략의 실체!", "", "알쓸잡",
                        "도와주는 방법입니다. 이를 위해서는 다음과 같은 전략을 사용할 수 있습니다. 1.키워드 연구: 사용자가 검색할 때 입력하는 단어나 구를 찾고, 그에 대한 <b>검색어</b>를 선정하여 적극적으로 활용합니다. 2.내부 링크 구축: 웹사이트 내의 페이지들 간에 링크를 구성하여 검색 엔진이 쉽게 페이지를 찾을 수 있도록 돕습니다...",
                        "http://wjdals10.tistory.com/2", LocalDateTime.now())), 1219700);


        when(blogSearchService.search(any(SearchVo.class))).thenReturn(Mono.just(response));

        mockMvc.perform(get("/blog")
                        .param("query", "검색어")
                        .param("sort", "정렬기준")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documents[0].title").value("충격!ChatGPT가 밝히는 구글 SEO전략의 실체!"))
                .andExpect(jsonPath("$.documents[0].thumbnail").value(""))
                .andExpect(jsonPath("$.documents[0].blogName").value("알쓸잡"))
                .andExpect(jsonPath("$.documents[0].contents").value("도와주는 방법입니다. 이를 위해서는 다음과 같은 전략을 사용할 수 있습니다. 1.키워드 연구: 사용자가 검색할 때 입력하는 단어나 구를 찾고, 그에 대한 <b>검색어</b>를 선정하여 적극적으로 활용합니다. 2.내부 링크 구축: 웹사이트 내의 페이지들 간에 링크를 구성하여 검색 엔진이 쉽게 페이지를 찾을 수 있도록 돕습니다..."))
                .andExpect(jsonPath("$.documents[0].url").value("http://wjdals10.tistory.com/2"));
    }

}
