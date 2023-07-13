package com.place.search.domain.converter

import com.place.search.presentation.external.request.KakaoLocalSearchRequest
import com.place.search.presentation.external.request.NaverLocalSearchRequest
import com.place.search.presentation.external.response.kakao.KakaoApiResponse
import com.place.search.presentation.external.response.kakao.KakaoLocalSearchDocumentResponse
import com.place.search.presentation.external.response.naver.NaverLocalSearchResponse
import com.place.search.presentation.internal.request.LocalSearchRequest
import com.place.search.presentation.internal.response.LocalSearchResponse
import org.springframework.stereotype.Component

@Component
class LocalSearchConverter {
    fun convertToKakao(request: LocalSearchRequest): KakaoLocalSearchRequest {
        return KakaoLocalSearchRequest(
            query = request.query,
            page = request.pageNum,
            size = request.resultsCnt,
        )
    }

    fun convertFromKakao(response: KakaoApiResponse<KakaoLocalSearchDocumentResponse>): Set<String> {
        return response.documents.map { it.place_name.replace(" ", "") }.toSet()
    }

    fun convertToNaver(request: LocalSearchRequest): NaverLocalSearchRequest {
        return NaverLocalSearchRequest(
            query = request.query,
            display = request.resultsCnt,
            start = request.pageNum,
        )
    }

    fun convertFromNaver(response: NaverLocalSearchResponse): Set<String> {
        return response.items.map {
            it.title.replace("<b>", "").replace("</b>", "").replace(" ", "")
        }.toSet()
    }
}
