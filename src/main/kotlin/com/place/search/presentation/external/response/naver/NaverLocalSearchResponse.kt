package com.place.search.presentation.external.response.naver

data class NaverLocalSearchResponse(
    // 검색 결과를 생성한 시간
    val lastBuildDate: String,

    // 총 검색 결과 개수
    val total : Long,

    // 검색 시작 위치
    val start: Long,

    // 한 번에 표시할 검색 결과 개수
    val display: Long,

    // JSON 형식의 결괏값에서는 items 속성의 JSON 배열로 개별 검색 결과를 반환합니다.
    val items: List<NaverLocalSearchItemResponse>,
)
