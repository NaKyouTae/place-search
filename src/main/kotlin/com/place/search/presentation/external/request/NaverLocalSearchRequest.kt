package com.place.search.presentation.external.request

data class NaverLocalSearchRequest(
    val query: String,

    // 한 번에 표시할 검색 결과 개수(기본값: 5, 최소값: 1, 최댓값: 5)
    val display: Long? = 5,

    // 검색 시작 위치(기본값: 1, 최댓값: 1)
    val start: Long? = 1,
)
