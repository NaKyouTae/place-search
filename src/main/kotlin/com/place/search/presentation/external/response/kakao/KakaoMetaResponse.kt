package com.place.search.presentation.external.response.kakao

data class KakaoMetaResponse(
    // 검색된 문서 수
    val total_count: Long,

    // total_count 중 노출 가능 문서 수 (최대값: 45)
    val pageable_count: Long,

    // 현재 페이지가 마지막 페이지인지 여부
    // 값이 false면 다음 요청 시 page 값을 증가시켜 다음 페이지 요청 가능
    val is_end: Boolean,

    // 질의어의 지역 및 키워드 분석 정보
    val same_name: KakaoSameNameResponse
)
