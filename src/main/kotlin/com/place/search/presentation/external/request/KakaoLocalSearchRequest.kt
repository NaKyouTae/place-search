package com.place.search.presentation.external.request

data class KakaoLocalSearchRequest(
    // 검색을 원하는 질의어	O
    val query: String,

    // 결과 페이지 번호
    // (최소: 1, 최대: 45, 기본대값: 1)
    val page: Int? = 5,

    // 한 페이지에 보여질 문서의 개수
    // (최소: 1, 최대: 45, 기본값: 15)
    val size: Int? = 1,
)
