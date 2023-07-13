package com.place.search.common

import com.place.search.presentation.external.response.kakao.KakaoApiResponse
import com.place.search.presentation.external.response.kakao.KakaoLocalSearchDocumentResponse
import com.place.search.presentation.external.response.kakao.KakaoMetaResponse
import com.place.search.presentation.external.response.kakao.KakaoSameNameResponse
import com.place.search.presentation.external.response.naver.NaverLocalSearchItemResponse
import com.place.search.presentation.external.response.naver.NaverLocalSearchResponse
import java.time.Instant

object DataGenerator {
    fun generateKakaoLocalSearchApiResponse(places: List<String>): KakaoApiResponse<KakaoLocalSearchDocumentResponse> {
        return KakaoApiResponse(
            meta = KakaoMetaResponse(
                total_count = 5,
                pageable_count = 44,
                is_end = false,
                same_name = KakaoSameNameResponse(
                    region = listOf(),
                    keyword = "",
                    selected_region = "",
                )
            ),
            documents = places.map {
                KakaoLocalSearchDocumentResponse(
                    id = "",
                    place_name = it,
                    category_name = "",
                    category_group_code = "",
                    category_group_name = "",
                    phone = "",
                    address_name = "",
                    road_address_name = "",
                    x = "",
                    y = "",
                    place_url = "",
                    distance = "",
                )
            }
        )
    }

    fun generateNaverLocalSearchApiResponse(places: List<String>): NaverLocalSearchResponse {
        return NaverLocalSearchResponse(
            lastBuildDate = Instant.now().toString(),
            total = 5,
            start = 1,
            display = 5,
            items = places.map {
                NaverLocalSearchItemResponse(
                    title = it,
                    link = "",
                    category = "",
                    description = "",
                    telephone = "",
                    address = "",
                    roadAddress = "",
                    mapx = 0,
                    mapy = 0,
                )
            }
        )
    }
}
