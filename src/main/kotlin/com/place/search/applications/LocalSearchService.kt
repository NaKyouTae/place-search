package com.place.search.applications

import com.place.search.domain.converter.LocalSearchConverter
import com.place.search.presentation.external.service.KakaoLocalService
import com.place.search.presentation.internal.request.LocalSearchRequest
import org.springframework.stereotype.Service

@Service
class LocalSearchService(
    private val kakaoLocalService: KakaoLocalService,
    private val localSearchConverter: LocalSearchConverter,
) {

    fun searchLocalByKeyword(request: LocalSearchRequest): List<String> {
        val kakaoLocalRequest = localSearchConverter.convert(request)
        val kakaoResponse = kakaoLocalService.searchPlaceByKakaoApi(kakaoLocalRequest)


    }
}
