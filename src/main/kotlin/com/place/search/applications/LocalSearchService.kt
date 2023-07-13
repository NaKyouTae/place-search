package com.place.search.applications

import com.place.search.domain.converter.LocalSearchConverter
import com.place.search.presentation.external.service.KakaoLocalService
import com.place.search.presentation.external.service.NaverLocalService
import com.place.search.presentation.internal.request.LocalSearchRequest
import org.springframework.stereotype.Service
import java.util.PriorityQueue

@Service
class LocalSearchService(
    private val kakaoLocalService: KakaoLocalService,
    private val naverLocalService: NaverLocalService,
    private val localSearchConverter: LocalSearchConverter,
) {

    fun searchLocalByKeyword(request: LocalSearchRequest): List<String> {
        val kakaoLocalRequest = localSearchConverter.convertToKakao(request)
        val kakaoResponse = kakaoLocalService.searchPlaceByKakaoApi(kakaoLocalRequest)
        val naverLocalRequest = localSearchConverter.convertToNaver(request)
        val naverResponse = naverLocalService.searchPlaceByNaverApi(naverLocalRequest)

        if(kakaoResponse == null && naverResponse == null) return emptyList()

        val kakaoLocalSearchResponse = localSearchConverter.convertFromKakao(kakaoResponse)
        val naverLocalSearchResponse = localSearchConverter.convertFromNaver(naverResponse)

        val unionList = kakaoLocalSearchResponse.intersect(naverLocalSearchResponse).toList()
        val aSub = kakaoLocalSearchResponse.minus(naverLocalSearchResponse).toList()
        val bSub = naverLocalSearchResponse.minus(kakaoLocalSearchResponse).toList()

        return unionList + aSub + bSub
    }


    data class Node(
        val index : Int,
        val value : String
    ) : Comparable<Node> {
        override fun compareTo(other: Node): Int = index - other.index

    }
}
