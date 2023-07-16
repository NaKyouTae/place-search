package com.place.search.presentation.internal.controller

import com.place.search.applications.LocalSearchService
import com.place.search.presentation.internal.request.LocalSearchRequest
import com.place.search.presentation.internal.response.CommonResponse
import com.place.search.presentation.internal.response.LocalSearchResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "장소 검색 API", description = "장소 검색 관련 API")
@ApiResponses(
    ApiResponse(responseCode = "200", description = "Success"),
    ApiResponse(responseCode = "404", content = [Content(schema = Schema())]),
    ApiResponse(responseCode = "500", content = [Content(schema = Schema())])
)
@RestController
@RequestMapping("/api/local/search")
class LocalSearchController(
    private val localSearchService: LocalSearchService,
) {
    @Operation(summary = "키워드 장소 검색 API", description = "키워드를 통해 장소를 검색하는 API")
    @PostMapping
    fun searchLocalByKeyword(@RequestBody request: LocalSearchRequest): CommonResponse<LocalSearchResponse> {
        val places = localSearchService.searchLocalByKeyword(request)
        return CommonResponse(
            isSuccess = true,
            message = "Success Location List Search !!",
            result = LocalSearchResponse(places)
        )
    }
}
