package com.place.search.presentation.internal.controller

import com.place.search.applications.SearchHistoryService
import com.place.search.presentation.internal.response.CommonResponse
import com.place.search.presentation.internal.response.SearchHistoryResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "검색 이력 API", description = "이전 검색한 이력 관련 API")
@ApiResponses(
    ApiResponse(responseCode = "200", description = "Success"),
    ApiResponse(responseCode = "404", content = [Content(schema = Schema())]),
    ApiResponse(responseCode = "500", content = [Content(schema = Schema())])
)
@RestController
@RequestMapping("/api/search/histories")
class SearchHistoryController(
    private val searchHistoryService: SearchHistoryService,
) {

    @Operation(summary = "가장 많이 조회한 검색 키워드 10개 조회 API", description = "사용자들이 가장 많이 조회한 검색 키워드를 선별하여 응답으로 내려주는 API")
    @GetMapping
    fun getSearchKeywordTop10(): CommonResponse<List<SearchHistoryResponse>> {
        val histories = searchHistoryService.getSearchKeywordTop10()
        return CommonResponse(
            isSuccess = true,
            message = "Success Search Histories !!",
            result = histories.map {
                SearchHistoryResponse(
                    id = it.id!!,
                    keyword = it.keyword,
                    views = it.views,
                    createdAt = it.createdAt.toEpochMilli(),
                    updatedAt = it.updatedAt?.toEpochMilli(),
                )
            }
        )
    }
}
