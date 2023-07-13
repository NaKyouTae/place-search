package com.place.search.presentation.internal.request

data class LocalSearchRequest(
    // 검색을 원하는 질의어	O
    val query: String,

    // 페이지 번호
    // (최소: 1, 최대: 1, 기본값: 1)
    val pageNum: Long? = 1,

    // 한 페이지의 결과 개수 (최소: 1, 최대: 5, 기본값: 5)
    val resultsCnt: Long? = 5,
)
