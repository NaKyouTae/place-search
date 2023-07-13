package com.place.search.presentation.external.response.kakao

data class KakaoSameNameResponse(
    // 질의어에서 인식된 지역의 리스트
    // (예: '중앙로 맛집' 에서 '중앙로'에 해당하는 지역 리스트)
    val region: List<String>,

    // 질의어에서 지역 정보를 제외한 키워드
    // (예: '중앙로 맛집' 에서 '맛집')
    val keyword: String,

    // 인식된 지역 리스트 중 현재 검색에 사용된 지역 정보
    val selected_region: String,

)
