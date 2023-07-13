package com.place.search.presentation.internal.controller

import com.place.search.applications.LocalSearchService
import com.place.search.presentation.internal.request.LocalSearchRequest
import com.place.search.presentation.internal.response.CommonResponse
import com.place.search.presentation.internal.response.LocalSearchResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/local/search")
class LocalSearchController(
    private val localSearchService: LocalSearchService,
) {

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
