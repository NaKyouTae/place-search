package com.place.search.presentation.internal.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "LocalSearchRequest", description = "검색 요청 값")
data class LocalSearchRequest(
    @Schema(name = "query", description = "검색을 원하는 질의어")
    val query: String,
)
