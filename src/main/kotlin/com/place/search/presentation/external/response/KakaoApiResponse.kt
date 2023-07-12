package com.place.search.presentation.external.response

data class KakaoApiResponse<T>(
    val meta: MetaResponse, // 응답 관련 정보
    val documents: List<T>, // 응답 결과
)
