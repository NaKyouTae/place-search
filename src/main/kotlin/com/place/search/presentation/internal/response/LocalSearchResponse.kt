package com.place.search.presentation.internal.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "LocalSearchResponse", description = "장소 검색 응답 값")
data class LocalSearchResponse(
    @Schema(name = "places", description = "검색한 장소 목록")
    val places: List<String> = listOf(),
)
