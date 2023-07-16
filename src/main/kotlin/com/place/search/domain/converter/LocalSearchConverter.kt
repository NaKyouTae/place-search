package com.place.search.domain.converter

import com.place.search.presentation.external.request.KakaoLocalSearchRequest
import com.place.search.presentation.external.request.NaverLocalSearchRequest
import com.place.search.presentation.external.response.kakao.KakaoApiResponse
import com.place.search.presentation.external.response.kakao.KakaoLocalSearchDocumentResponse
import com.place.search.presentation.external.response.naver.NaverLocalSearchResponse
import com.place.search.presentation.internal.request.LocalSearchRequest
import org.springframework.stereotype.Component

@Component
class LocalSearchConverter {
    fun convertToKakao(request: LocalSearchRequest, size: Int?): KakaoLocalSearchRequest {
        return KakaoLocalSearchRequest(
            query = request.query,
            size = size,
        )
    }

    fun convertFromKakao(response: KakaoApiResponse<KakaoLocalSearchDocumentResponse>): Set<String> {
        return response.documents.map { PLACE_NAME_REFINE_REGEX.replace(it.place_name, "") }.toSet()
    }

    fun convertToNaver(request: LocalSearchRequest, size: Int?): NaverLocalSearchRequest {
        return NaverLocalSearchRequest(
            query = request.query,
            display = size,
        )
    }

    fun convertFromNaver(response: NaverLocalSearchResponse): Set<String> {
        return response.items.map {
            PLACE_NAME_REFINE_REGEX.replace(it.title, "")
        }.toSet()
    }

    companion object {
        val PLACE_NAME_REFINE_REGEX = Regex("<[^>]*>?|[^a-zA-Z가-힣\\s]")
    }
}
