package io.khw.search.blogsearch.router;

import io.khw.search.blogsearch.handler.BlogSearchHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class BlogSearchRouter {

    @Bean
    public RouterFunction<ServerResponse> blogSearchRoutes(BlogSearchHandler blogSearchHandler){
        return RouterFunctions.route()
                .GET("/search/blog", blogSearchHandler::blogSearch)
                .GET("/search/blog/top-keywords", blogSearchHandler::searchBlogTopKeywords)
                .build();
    }
}
