package com.place.search.presentation.external.response.naver

data class NaverLocalSearchItemResponse(
    // 업체, 기관의 이름
    val title: String,

    // 업체, 기관의 상세 정보 URL
    val link: String,

    // 업체, 기관의 분류 정보
    val category: String,

    // 업체, 기관에 대한 설명
    val description: String,

    // 값을 반환하지 않는 요소. 하위 호환성을 유지하기 위해 있는 요소입니다.
    val telephone: String,

    // 업체, 기관명의 지번 주소
    val address: String,

    // 업체, 기관명의 도로명 주소
    val roadAddress: String,

    // 업체, 기관이 위치한 장소의 x 좌표(KATECH 좌표계 기준). 네이버 지도 API에서 사용할 수 있습니다.
    val mapx: Long,

    // 업체, 기관이 위치한 장소의 y 좌표(KATECH 좌표계 기준). 네이버 지도 API에서 사용할 수 있습니다.
    val mapy: Long,
)
