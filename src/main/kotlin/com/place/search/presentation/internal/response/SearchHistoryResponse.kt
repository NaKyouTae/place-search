package com.place.search.presentation.internal.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "SearchHistoriesResponse", description = "키워드 검색 목록 응답 ")
data class SearchHistoryResponse(
    @Schema(name = "id", description = "검색 이력의 아이디", maxLength = 16)
    val id: String,

    @Schema(name = "isSuccess", description = "검색 이력의 키워드")
    val keyword: String,

    @Schema(name = "isSuccess", description = "검색 이력의 검색 횟수")
    val views: Long,

    @Schema(name = "isSuccess", description = "검색 이력의 생성 일자")
    val createdAt: Long,

    @Schema(name = "isSuccess", description = "검색 이력의 수정 일자", nullable = true)
    val updatedAt: Long?,
)
