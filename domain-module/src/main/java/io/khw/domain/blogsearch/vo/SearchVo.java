package io.khw.domain.blogsearch.vo;import io.khw.common.constants.ApiResponseCode;import io.khw.common.exeception.ApiException;import io.swagger.v3.oas.annotations.media.Schema;@Schema(description = "검색 VO")public record SearchVo(        @Schema(description = "준", example = "검색어")        String query,        @Schema(description = "정렬기준", example = "카카오 API(accuracy(정확도순), recency(최신순)), 네이버 API(sim(정확도순), date(최신순))")        String sort,        @Schema(description = "페이지 번호", example = "1~50")        int page,        @Schema(description = "표출 개수", example = "1~50")        int size) {    public static SearchVo create(String query, String sort, int page, int size) {        if (query == null || query.isBlank()) {            throw new ApiException(ApiResponseCode.INVALID_QUERY_VALUE);        }        if (sort == null || sort.isBlank()) {            throw new ApiException(ApiResponseCode.INVALID_SORT_VALUE);        }        if (page < 1 || page > 50) {            throw new ApiException(ApiResponseCode.INVALID_PAGE_VALUE);        }        if (size < 10 || size > 50) {            throw new ApiException(ApiResponseCode.INVALID_SIZE_VALUE);        }        return new SearchVo(query, sort, page, size);    }}